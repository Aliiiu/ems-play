package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import dtos.AttendanceDTO;
import dtos.EmployeeDTO;
import interceptor.EMSAction;
import models.Role;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;
import services.AttendanceService;
import services.EmployeeService;
import utils.Helper;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@With(EMSAction.class)
public class EmployeeController extends Controller {
    private HttpExecutionContext executionContext;
    private EmployeeService employeeService;
    private AttendanceService attendanceService;

    @Inject
    public EmployeeController(HttpExecutionContext executionContext, EmployeeService employeeService, AttendanceService attendanceService){
        this.executionContext = executionContext;
        this.employeeService = employeeService;
        this.attendanceService = attendanceService;
    }

    public CompletionStage<Result> addEmployee(Http.Request request){
        JsonNode jsonData = request.body().asJson();
        if (!(jsonData.has("firstName") && jsonData.has("lastName") && jsonData.has("role") && jsonData.has("email"))){
            return CompletableFuture.completedFuture(badRequest(Helper.createResponse("One or more of this field is missing: firstname, lastname, role, email", false)));
        }

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setFirstName(jsonData.get("firstName").asText());
        employeeDTO.setLastName(jsonData.get("lastName").asText());
        employeeDTO.setEmail(jsonData.get("email").asText());
        employeeDTO.setRole(Role.fromString(jsonData.get("role").asText().toLowerCase()));
        employeeDTO.setCreatedAt(LocalDateTime.now());
        if(employeeDTO.getRole() == null){
            return CompletableFuture.completedFuture(badRequest(Helper.createResponse("Role does not exist", false)));
        }
        CompletionStage<Optional<EmployeeDTO>> employeeOptional = employeeService.findEmployeeByEmail(employeeDTO.getEmail());
        return employeeOptional.thenComposeAsync(optionalEmployeeDTO -> {
            if (optionalEmployeeDTO.isPresent()) {
                return CompletableFuture.completedFuture(badRequest(Helper.createResponse("Email already exists", false)));
            } else {
                employeeDTO.setPassword(Helper.generatePin());
                return employeeService.create(employeeDTO)
                        .thenApplyAsync(savedData -> created(Helper.createResponse(Json.toJson(savedData), true)), executionContext.current());
            }
        }, executionContext.current());
    }

    public CompletionStage<Result> removeEmployee(int id){
        CompletionStage<Optional<EmployeeDTO>> optionalCompletionStage = employeeService.findEmployeeById(Long.parseLong(String.valueOf(id)));
        return optionalCompletionStage.thenComposeAsync(optionalEmployeeDTO -> {
            if (optionalEmployeeDTO.isPresent()){
                employeeService.delete(Long.parseLong(String.valueOf(id)));
                return CompletableFuture.completedFuture(ok(Helper.createResponse("Employee deleted successfully", true)));
            } else {
                return CompletableFuture.completedFuture(notFound(Helper.createResponse("Employee not found", false)));
            }
        }, executionContext.current());
    }

    public CompletionStage<Result> updatePassword(Http.Request request){
        JsonNode jsonData = request.body().asJson();
        if (!jsonData.has("token")){
            return CompletableFuture.completedFuture(unauthorized(Helper.createResponse("Invalid token", false)));
        } else {
            String email = jsonData.get("email").asText();
            String password = jsonData.get("password").asText();
            CompletionStage<Optional<EmployeeDTO>> employeeOptional = employeeService.findEmployeeByEmail(email);
            return employeeOptional.thenComposeAsync(employeeOptionalDTO -> {
                if (employeeOptionalDTO.isPresent()){
                    EmployeeDTO employeeDTO = employeeOptionalDTO.get();
                    if (employeeDTO.getPassword().equals(password)){
                        return CompletableFuture.completedFuture(badRequest(Helper.createResponse("enter a new password", false)));
                    } else {
                        employeeDTO.setPassword(password);
                        return employeeService.update(employeeDTO).thenComposeAsync(optionalData -> {
                            if (optionalData.isPresent()){
                                return CompletableFuture.completedFuture(ok(Helper.createResponse("Password updated successfully", true)));
                            } else {
                                return CompletableFuture.completedFuture(internalServerError(Helper.createResponse("Error changing password", false)));
                            }
                        }, executionContext.current());
                    }
                } else {
                    return CompletableFuture.completedFuture(badRequest(Helper.createResponse("Invalid Credentials", false)));
                }
            }, executionContext.current());
        }
    }


    public CompletionStage<Result> signIn(Http.Request request) {
        JsonNode jsonData = request.body().asJson();
        if (!jsonData.has("password")){
            return CompletableFuture.completedFuture(badRequest(Helper.createResponse("Provide a valid password", false)));
        } else {
            String email = jsonData.get("email").asText();
            String password = jsonData.get("password").asText();
            CompletionStage<Optional<EmployeeDTO>> employeeOptional = employeeService.findEmployeeByEmail(email);
            return employeeOptional.thenComposeAsync(employeeOptionalDTO -> {
                if (employeeOptionalDTO.isPresent()) {
                    EmployeeDTO employeeDTO = employeeOptionalDTO.get();
                    if (employeeDTO.getPassword().equals(password)) {
                        String token = UUID.randomUUID().toString();
                        employeeDTO.setToken(token);
                        return employeeService.update(employeeDTO).thenApplyAsync(optionData -> {
                            if (optionData.isPresent()) {
                                return created(Helper.createResponse(Json.toJson(optionData.get()), true));
                            } else {
                                return internalServerError(Helper.createResponse("Error updating employee", false));
                            }
                        }, executionContext.current());
                    } else {
                        return CompletableFuture.completedFuture(badRequest(Helper.createResponse("Wrong password", false)));
                    }
                } else {
                    return CompletableFuture.completedFuture(badRequest(Helper.createResponse("Invalid Credentials", false)));
                }
            }, executionContext.current());
        }
    }

    public CompletionStage<Result> getAllEmployees(){
        return employeeService.getAllEmployees().thenApplyAsync(employeeDTOStream -> {
            List<EmployeeDTO> employeeDTOList = employeeDTOStream.collect(Collectors.toList());
            return ok(Helper.createResponse(Json.toJson(employeeDTOList), true));
        }, executionContext.current());
    }

    public CompletionStage<Result> getEmployee(int id){
        CompletionStage<Optional<EmployeeDTO>> optionalCompletionStage = employeeService.findEmployeeById(Long.parseLong(String.valueOf(id)));
        return optionalCompletionStage.thenComposeAsync(optionalEmployeeDTO -> {
            if (optionalEmployeeDTO.isPresent()){
                EmployeeDTO attendanceDTO = optionalEmployeeDTO.get();
                return CompletableFuture.completedFuture(ok(Helper.createResponse(Json.toJson(attendanceDTO), true)));
            } else {
                return CompletableFuture.completedFuture(notFound(Helper.createResponse("Employee not found", false)));
            }
        }, executionContext.current());
    }

    public CompletionStage<Result> getEmployeeDailyAttendance(){
        LocalDate localDate = LocalDate.now();
        CompletionStage<Optional<List<AttendanceDTO>>> optionalCompletionStage = attendanceService.findByEmployeeAndDate(localDate);
        return optionalCompletionStage.thenApplyAsync(optionalAttendanceStream -> {
            if (optionalAttendanceStream.isPresent()) {
                List<AttendanceDTO> attendanceList = optionalAttendanceStream.get();
                return ok(Json.toJson(attendanceList));
            } else {
                return notFound(Helper.createResponse("Attendance records not found", false));
            }
        });

    }

    public CompletionStage<Result> markAttendance(Http.Request request){
        JsonNode jsonData = request.body().asJson();
        if (!jsonData.has("token")){
            return CompletableFuture.completedFuture(unauthorized("Unauthorized request"));
        } else {
            if (!jsonData.has("employeeId")){
                return CompletableFuture.completedFuture(badRequest("EmployeeId is not specified"));
            } else {
                Long employeeId = jsonData.get("employeeId").asLong();
                AttendanceDTO attendanceDTO = new AttendanceDTO();
                LocalDateTime currentTime = LocalDateTime.now();

                if (!Helper.isWorkingDay(currentTime)) {
                    return CompletableFuture.completedFuture(badRequest(Helper.createResponse("Attendance cannot be marked on non-working days", true)));
                }

                if (!Helper.isWorkingHours(currentTime)) {
                    return CompletableFuture.completedFuture((badRequest(Helper.createResponse("Attendance cannot be marked outside working hours", false))));
                }

                attendanceDTO.setDate(LocalDate.now());
                attendanceDTO.setTimeIn(LocalDateTime.now());
                CompletionStage<Optional<EmployeeDTO>> employeeOptional = employeeService.findEmployeeById(employeeId);
                return employeeOptional.thenComposeAsync(optionalEmployeeDTO -> {
                    if (optionalEmployeeDTO.isPresent()){
                        attendanceDTO.setEmployeeDTO(optionalEmployeeDTO.get());
//                        return CompletableFuture.completedFuture(created(Helper.createResponse(Json.toJson(attendanceDTO), true)));
                        return attendanceService.save(attendanceDTO).thenApplyAsync(savedData -> created(Helper.createResponse(Json.toJson(savedData), true)));
                    } else {
                        return CompletableFuture.completedFuture(badRequest(Helper.createResponse("Employee does not exit", false)));
                    }
                }, executionContext.current());
            }
        }
    }
}



