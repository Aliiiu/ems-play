package repository;

import models.Employee;
import net.jodah.failsafe.CircuitBreaker;
import net.jodah.failsafe.Failsafe;
import play.db.jpa.JPAApi;
import utils.DatabaseExecutionContext;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Singleton
public class EmployeeRepositoryImp implements EmployeeRepository{
    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;
    private final CircuitBreaker<Optional<Employee>> circuitBreaker = new CircuitBreaker<Optional<Employee>>().withFailureThreshold(1).withSuccessThreshold(3);

    @Inject
    public EmployeeRepositoryImp(JPAApi jpaApi, DatabaseExecutionContext executionContext){
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Stream<Employee>> getAllEmployees(){
        return supplyAsync(() -> wrap(this::select), executionContext);
    }

    @Override
    public CompletionStage<Employee> create (Employee employeeData){
        return supplyAsync(() -> wrap(entityManager -> insert(entityManager, employeeData)), executionContext);
    }

    @Override
    public CompletionStage<Optional<Employee>> update(Long id, Employee employeeData){
        return supplyAsync(() -> wrap(entityManager -> Failsafe.with(circuitBreaker).get(() -> modify(entityManager, id, employeeData))), executionContext);
    }

    @Override
    public CompletionStage<Optional<Employee>> findEmployeeById(Long id) {
        return supplyAsync(() -> wrap(em -> Failsafe.with(circuitBreaker).get(() -> lookup(em, id))), executionContext);
    }

    @Override
    public CompletionStage<Optional<Employee>> findEmployeeByEmail(String email) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return wrap(em -> Failsafe.with(circuitBreaker).get(() -> search(em, email)));
            } catch (NoResultException e) {
                return Optional.empty();
            }
        }, executionContext);
    }

    @Override
    public CompletionStage<Void> deleteEmployee(Long id){
        CompletionStage<Void> completionStage = supplyAsync(() -> wrap(entityManager -> {
            Employee employee = entityManager.find(Employee.class, id);
            if (employee != null){
                delete(entityManager, employee);
            }
            return null;
        }), executionContext);

        return completionStage;
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Optional<Employee> lookup(EntityManager em, Long id) throws SQLException {
        return Optional.ofNullable(em.find(Employee.class, id));
    }

    private Optional<Employee> search(EntityManager em, String email) throws SQLException {
        TypedQuery<Employee> query = em
                .createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                .setParameter("email", email);
        return Optional.ofNullable(query.getSingleResult());
    }

    private Stream<Employee> select(EntityManager em) {
        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList().stream();
    }

    private Optional<Employee> modify(EntityManager em, Long id, Employee employee) {
        Employee data = em.find(Employee.class, id);
        if (data != null) {
            data.setPassword(employee.getPassword());
            data.setToken(employee.getToken());
            em.merge(data);
        }
        return Optional.ofNullable(data);
    }

    private Employee insert(EntityManager em, Employee postData) {
        return em.merge(postData);
    }

    private void delete(EntityManager em, Employee employeeData){
        em.remove(employeeData);
    }

}
