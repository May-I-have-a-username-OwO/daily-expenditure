package com.aid.daily_expenditure;

import org.springframework.data.repository.CrudRepository;

public interface RecordRepository extends CrudRepository<Record, String> {
}