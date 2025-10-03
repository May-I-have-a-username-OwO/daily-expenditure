package com.aid.daily_expenditure;

import org.springframework.data.repository.CrudRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface RecordRepository extends CrudRepository<Record, String> {
    List<Record> findAll();
    List<Record> findByDateTimeBetween(ZonedDateTime start, ZonedDateTime end);
}