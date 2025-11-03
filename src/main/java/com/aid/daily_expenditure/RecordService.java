package com.aid.daily_expenditure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    void addRecord(Record record) {
        recordRepository.save(record);
    }

    List<Record> fetchAllRecord() {
        return recordRepository.findAll();
    }

    List<Record> fetchRecordByExactDate(ZonedDateTime start, ZonedDateTime end) {
        return recordRepository.findByDateTimeBetween(start, end);
    }

//    void updateRecord(Record record, String id);

    void deleteRecordById(String id) {
        recordRepository.deleteById(id);
    }

    ZonedDateTime fetchOldestDate() {
        return recordRepository.findTopByOrderByDateTimeAsc().get().getDateTime();
    }
}