package repository;

import models.Employee;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface EmployeeRepository {
    CompletionStage<Stream<Employee>> getAllEmployees();
    CompletionStage<Employee> create(Employee employee);
    CompletionStage<Optional<Employee>> findEmployeeById(Long id);
    CompletionStage<Optional<Employee>> findEmployeeByEmail(String email);
    CompletionStage<Optional<Employee>> update(Long id, Employee employeeData);
    CompletionStage<Void> deleteEmployee(Long id);
}
