package com.aid.daily_expenditure.Record;

import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends CrudRepository<Record, String> {
    //List<Record> findAll();
    //List<Record> findByDateTimeBetween(ZonedDateTime start, ZonedDateTime end);

    List<Record> findByUserIdOrderByDateTimeDesc(String userId);
    List<Record> findByDateTimeBetweenAndUserIdOrderByDateTimeDesc(ZonedDateTime start, ZonedDateTime end, String userId);

    Optional<Record> findTopByUserIdOrderByDateTimeAsc(String userId);
}