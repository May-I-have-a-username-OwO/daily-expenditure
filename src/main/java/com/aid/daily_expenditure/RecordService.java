package com.aid.daily_expenditure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    void addRecord(Record record) {
        recordRepository.save(record);
    }

//    List<Record> fetchRecordList();

//    void updateRecord(Record record, String id);

    void deleteRecordById(String id) {
        recordRepository.deleteById(id);
    }
}