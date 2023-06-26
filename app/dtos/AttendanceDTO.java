package dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceDTO {
    private Long attendanceId;
    private LocalDate date;
    private LocalDateTime timeIn;
    private EmployeeDTO employeeDTO;

    public AttendanceDTO(){}

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(LocalDateTime timeIn) {
        this.timeIn = timeIn;
    }

    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public void setEmployeeDTO(EmployeeDTO employeeDTOId) {
        this.employeeDTO = employeeDTOId;
    }

    @Override
    public String toString() {
        return "AttendanceDTO{" +
                "attendanceId=" + attendanceId +
                ", date=" + date +
                ", timeIn=" + timeIn +
                '}';
    }
}
