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

    public List<Record> fetchAllRecord(String userId) {
        return recordRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    public List<Record> fetchRecordByExactDate(ZonedDateTime start, ZonedDateTime end, String userId) {
        return recordRepository.findByDateTimeBetweenAndUserIdOrderByDateTimeDesc(start, end, userId);
    }

//    void updateRecord(Record record, String id);
/*
    void deleteRecordById(String id) {
        recordRepository.deleteById(id);
    }

 */

    public ZonedDateTime fetchOldestDate(String userId) {
        return recordRepository.findTopByUserIdOrderByDateTimeAsc(userId).get().getDateTime();
    }
}