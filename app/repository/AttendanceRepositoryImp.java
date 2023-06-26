package repository;

import models.Attendance;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Singleton
public class AttendanceRepositoryImp implements AttendanceRepository {
    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;
    private final CircuitBreaker<Optional<Attendance>> circuitBreaker = new CircuitBreaker<Optional<Attendance>>().withFailureThreshold(1).withSuccessThreshold(3);

    @Inject
    public AttendanceRepositoryImp(JPAApi jpaApi, DatabaseExecutionContext executionContext){
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Attendance> save (Attendance attendance){
        return supplyAsync(() -> wrap(em -> insert(em, attendance)), executionContext);
    }

    @Override
    public CompletionStage<Optional<List<Attendance>>> findByEmployeeAndDate(LocalDate date){
        return CompletableFuture.supplyAsync(() -> {
            try {
                return wrap(em -> search(em, date));
            } catch (NoResultException e) {
                return Optional.empty();
            }
        }, executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Optional<Attendance> lookup(EntityManager em, Long id) throws SQLException {
        return Optional.ofNullable(em.find(Attendance.class, id));
    }

    private Optional<List<Attendance>> search(EntityManager em, LocalDate date) {
        TypedQuery<Attendance> query = em
                .createQuery("SELECT a  FROM Attendance a WHERE a.date = :date", Attendance.class)
                .setParameter("date", date);
        return Optional.ofNullable(query.getResultList());
    }

    private Stream<Attendance> select(EntityManager em) {
        TypedQuery<Attendance> query = em.createQuery("SELECT e FROM Employee e", Attendance.class);
        return query.getResultList().stream();
    }

    private Attendance insert(EntityManager em, Attendance attendance) {
        return em.merge(attendance);
    }

    private void delete(EntityManager em, Attendance attendance){
        em.remove(attendance);
    }
}
