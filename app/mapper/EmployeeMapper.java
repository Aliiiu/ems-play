package mapper;

import dtos.EmployeeDTO;
import models.Employee;

public class EmployeeMapper {
    public static EmployeeDTO toEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeID());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setToken(employee.getToken());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setCreatedAt(employee.getCreatedAt());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        return employeeDTO;
    }

    public static Employee fromEmployeeDTO(EmployeeDTO employee){
        Employee employee1 = new Employee();
        employee1.setEmployeeID(employee.getEmployeeId());
        employee1.setEmail(employee.getEmail());
        employee1.setPassword(employee.getPassword());
        employee1.setToken(employee.getToken());
        employee1.setRole(employee.getRole());
        employee1.setFirstName(employee.getFirstName());
        employee1.setLastName(employee.getLastName());
        employee1.setCreatedAt(employee.getCreatedAt());
        return employee1;
    }
}


