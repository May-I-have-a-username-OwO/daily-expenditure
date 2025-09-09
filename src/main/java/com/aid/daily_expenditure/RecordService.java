package com.aid.daily_expenditure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

//    void updateRecord(Record record, String id);

    void deleteRecordById(String id) {
        recordRepository.deleteById(id);
    }
}