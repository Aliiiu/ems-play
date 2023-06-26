package services;

import dtos.EmployeeDTO;
import mapper.EmployeeMapper;
import models.Employee;
import play.libs.concurrent.HttpExecutionContext;
import repository.EmployeeRepository;

import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private HttpExecutionContext executionContext;

    @Inject
    public EmployeeService(EmployeeRepository employeeRepository, HttpExecutionContext executionContext){
        this.employeeRepository = employeeRepository;
        this.executionContext = executionContext;
    }

    public CompletionStage<EmployeeDTO> create(EmployeeDTO employeeDTO){
        Employee employee = EmployeeMapper.fromEmployeeDTO(employeeDTO);
        return employeeRepository.create(employee).thenApplyAsync(savedData -> {
            return EmployeeMapper.toEmployeeDTO(savedData);
        }, executionContext.current());
    }

    public CompletionStage<Stream<EmployeeDTO>> getAllEmployees(){
        return employeeRepository.getAllEmployees().thenApplyAsync(dataStream -> {
            return dataStream.map(data -> EmployeeMapper.toEmployeeDTO(data));
        }, executionContext.current());
    }

    public CompletionStage<Optional<EmployeeDTO>> findEmployeeById(Long id){
        return employeeRepository.findEmployeeById(id).thenApplyAsync(optionalData -> {
            return optionalData.map(data -> EmployeeMapper.toEmployeeDTO(data));
        }, executionContext.current());
    }

    public CompletionStage<Optional<EmployeeDTO>> findEmployeeByEmail(String email){
        return employeeRepository.findEmployeeByEmail(email).thenApplyAsync(optionalData -> {
            if (optionalData.isEmpty()) {
                return Optional.empty();
            } else {
                return optionalData.map(data -> EmployeeMapper.toEmployeeDTO(data));
            }
        }, executionContext.current());
    }

    public CompletionStage<Optional<EmployeeDTO>> update(EmployeeDTO employeeDTO){
        Employee employee = EmployeeMapper.fromEmployeeDTO(employeeDTO);
        return employeeRepository.update(employee.getEmployeeID(), employee).thenApplyAsync(optionalData ->{
            return optionalData.map(data -> EmployeeMapper.toEmployeeDTO(data));
        }, executionContext.current());
    }

    public void delete(Long id){
        employeeRepository.deleteEmployee(id);
    }
}
