package com.aid.daily_expenditure.Record;

import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends CrudRepository<Record, String> {
    @Override
    List<Record> findAll();
    List<Record> findByDateTimeBetween(ZonedDateTime start, ZonedDateTime end);

    List<Record> findByUserId(String userId);
    List<Record> findByUserIdAndDateTimeBetween(String userId, ZonedDateTime start, ZonedDateTime end);

    Optional<Record> findTopByOrderByDateTimeAsc();
}