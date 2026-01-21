package com.aid.daily_expenditure.Record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public void addRecord(Record record) {
        recordRepository.save(record);
    }

    public List<Record> fetchAllRecord() {
        return recordRepository.findAll();
    }

    public List<Record> fetchRecordByExactDate(ZonedDateTime start, ZonedDateTime end) {
        return recordRepository.findByDateTimeBetween(start, end);
    }

//    void updateRecord(Record record, String id);
/*
    void deleteRecordById(String id) {
        recordRepository.deleteById(id);
    }

 */

    public ZonedDateTime fetchOldestDate() {
        return recordRepository.findTopByOrderByDateTimeAsc().get().getDateTime();
    }
}