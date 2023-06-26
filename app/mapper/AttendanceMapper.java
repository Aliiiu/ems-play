package mapper;

import dtos.AttendanceDTO;
import models.Attendance;

import java.util.ArrayList;
import java.util.List;

public class AttendanceMapper {

    public static AttendanceDTO toAttendanceDTO(Attendance attendance){
        AttendanceDTO attendanceDTO = new AttendanceDTO();
        attendanceDTO.setAttendanceId(attendance.getAttendanceId());
        attendanceDTO.setDate(attendance.getDate());
        attendanceDTO.setTimeIn(attendance.getTimeIn());
        attendanceDTO.setEmployeeDTO(EmployeeMapper.toEmployeeDTO(attendance.getEmployee()));
        return attendanceDTO;
    }

    public static List<AttendanceDTO> toAttendanceDTOList(List<Attendance> attendanceList){
        List<AttendanceDTO> attendanceDTOList = new ArrayList<>();
        for (Attendance attendance : attendanceList){
            attendanceDTOList.add(toAttendanceDTO(attendance));
        }
        return attendanceDTOList;
    }

    public static List<Attendance> fromAttendanceDTOList(List<AttendanceDTO> attendanceDTOList){
        List<Attendance> attendanceList = new ArrayList<>();
        for (AttendanceDTO attendance : attendanceDTOList){
            attendanceList.add(fromAttendanceDTO(attendance));
        }
        return attendanceList;
    }

    public static Attendance fromAttendanceDTO(AttendanceDTO attendanceDTO){
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(attendanceDTO.getAttendanceId());
        attendance.setDate(attendanceDTO.getDate());
        attendance.setTimeIn(attendanceDTO.getTimeIn());
        attendance.setEmployee(EmployeeMapper.fromEmployeeDTO(attendanceDTO.getEmployeeDTO()));
        return attendance;
    }

}
