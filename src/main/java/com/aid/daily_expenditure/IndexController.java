package com.aid.daily_expenditure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String returnHomePage() {
        return "index";
        //return "redirect:/intro";
    }

    @GetMapping("/intro")
    public String returnIntroPage() {
        return "intro";
    }
/*
    @GetMapping("/login")
    public String returnLoginPage() {
        return "login";
    }

    @PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded")
    public String login(@RequestParam String userIDorEmail,
                        @RequestParam String password) {
        System.out.println(userIDorEmail);
        System.out.println(password);
        return "redirect:/";
    }

    @GetMapping("/signUp")
    public String returnSignUpPage() {
        return "signUp";
    }

    @PostMapping(value = "/signUpValidation", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String signUpValidation(@RequestParam String userId,
                                   @RequestParam String email,
                                   @RequestParam String password) {

        if (userService.idUsed(userId)) {
            return "idUsed";
        }
        if (userService.emailUsed(email)) {
            return "This email is used. Try another one.";
        }
        return "Ok";
    }

    @PostMapping(value = "/signUp", consumes = "application/x-www-form-urlencoded")
    public String signUp(@ModelAttribute User user) {
        userService.addUser(user);
        //return "idk";
        return null;
    }
*/
    @PostMapping("/addRecord")
    @ResponseBody
    public String addRecord(@RequestBody Record recordData) {
        recordService.addRecord(recordData);
        return "Record Added!";
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
