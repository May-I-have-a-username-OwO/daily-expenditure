package com.aid.daily_expenditure.Record;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;


@Controller
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/addRecord")
    public String addRecordPage() {
        return "addRecord";
    }

    @PostMapping("/addRecord")
    @ResponseBody
    public ResponseEntity<String> addRecord(
            @Valid @RequestBody Record record,
            @AuthenticationPrincipal UserDetails userDetails) {
        record.setUserId(userDetails.getUsername());
        recordService.addRecord(record);
        return new ResponseEntity<>("Record Added!", HttpStatus.CREATED);
    }

    @GetMapping("/recordSummary")
    public String recordSummaryPage() {
        return "recordSummary";
    }

    @GetMapping("/allRecord")
    @ResponseBody
    public List<Record> sendAllRecord(@AuthenticationPrincipal UserDetails userDetails) {
        return recordService.fetchAllRecord(userDetails.getUsername());
    }

    @GetMapping("/record")
    @ResponseBody
    public List<Record> sendRecordBetweenDateTime(
            @RequestParam ZonedDateTime start,
            @RequestParam ZonedDateTime end,
            @AuthenticationPrincipal UserDetails userDetails) {
        return recordService.fetchRecordByExactDate(start, end, userDetails.getUsername());
    }

    @GetMapping("/record/oldestDate")
    @ResponseBody
    public ZonedDateTime sendOldestDate(@AuthenticationPrincipal UserDetails userDetails) {
        return recordService.fetchOldestDate(userDetails.getUsername());
    }
}
