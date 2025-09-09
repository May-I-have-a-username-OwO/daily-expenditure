package com.aid.daily_expenditure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/")
    public String returnHomePage(Model model) {
        return "index";
    }

    @PostMapping("/addRecord")
    @ResponseBody
    public String addRecord(@RequestBody Record recordData) {
        System.out.println(recordData.getRecordType());
        System.out.println(recordData.getAmount());
        System.out.println(recordData.getDateTime());
        System.out.println(recordData.getPurpose());
        System.out.println(recordData.getRemark());
        recordService.addRecord(recordData);
        return "0k";
    }

    @GetMapping("/allRecord")
    @ResponseBody
    public List<Record> sendAllRecord() {
        return recordService.fetchAllRecord();
    }
}
