package services;

import com.fasterxml.jackson.databind.JsonNode;
import dtos.AttendanceDTO;
import dtos.EmployeeDTO;
import mapper.AttendanceMapper;
import models.Attendance;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Http;
import repository.AttendanceRepository;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public class AttendanceService {
    private AttendanceRepository attendanceRepository;
    private HttpExecutionContext executionContext;

    @Inject
    public AttendanceService (AttendanceRepository attendanceRepository, HttpExecutionContext executionContext){
        this.attendanceRepository = attendanceRepository;
        this.executionContext = executionContext;
    }

    public CompletionStage<AttendanceDTO> save(AttendanceDTO attendanceDTO){
        Attendance attendance = AttendanceMapper.fromAttendanceDTO(attendanceDTO);
        return attendanceRepository.save(attendance).thenApplyAsync(data -> {
            return AttendanceMapper.toAttendanceDTO(data);
        }, executionContext.current());
    }

    public CompletionStage<Optional<List<AttendanceDTO>>> findByEmployeeAndDate(LocalDate date){

        return attendanceRepository.findByEmployeeAndDate(date).thenApplyAsync(optionalData -> {
            if (optionalData.isEmpty()){
                return Optional.empty();
            } else {
                return optionalData.map(dataStream -> AttendanceMapper.toAttendanceDTOList(dataStream));
            }
        }, executionContext.current());
    }
}
