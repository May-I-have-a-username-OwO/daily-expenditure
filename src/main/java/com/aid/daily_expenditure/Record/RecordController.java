package com.aid.daily_expenditure.Record;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;


@Controller
public class RecordController {

    @Autowired
    private RecordService recordService;

    @PostMapping("/addRecord")
    @ResponseBody
    public ResponseEntity<String> addRecord(@Valid @RequestBody com.aid.daily_expenditure.Record.Record recordData) {
        recordService.addRecord(recordData);
        return new ResponseEntity<>("Record Added!", HttpStatus.CREATED);
    }

    @GetMapping("/allRecord")
    @ResponseBody
    public List<Record> sendAllRecord() {
        return recordService.fetchAllRecord();
    }

    @GetMapping("/record")
    @ResponseBody
    public List<Record> sendRecordBetweenDateTime(
            @RequestParam ZonedDateTime start,
            @RequestParam ZonedDateTime end) {
        return recordService.fetchRecordByExactDate(start, end);
    }

    @GetMapping("/record/oldestDate")
    @ResponseBody
    public ZonedDateTime sendOldestDate() {
        return recordService.fetchOldestDate();
    }
}
