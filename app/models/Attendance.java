package models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long attendanceId;
    private LocalDate date;
    private LocalDateTime timeIn;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Attendance(){}
    public Attendance(Employee employee, LocalDate date, LocalDateTime timeIn){
        this.employee = employee;
        this.date = date;
        this.timeIn = timeIn;
    }

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + attendanceId +
                ", date=" + date +
                ", timeIn=" + timeIn +
                ", employee=" + employee +
                '}';
    }
}
