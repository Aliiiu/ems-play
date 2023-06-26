package repository;

import models.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

public interface AttendanceRepository {
    CompletionStage<Attendance> save(Attendance attendance);

//    CompletionStage<Stream<Attendance>> getAllAttendance();

    CompletionStage<Optional<List<Attendance>>> findByEmployeeAndDate(LocalDate date);
}
