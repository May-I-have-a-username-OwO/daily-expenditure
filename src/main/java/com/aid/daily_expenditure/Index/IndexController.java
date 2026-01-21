package com.aid.daily_expenditure.Index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @GetMapping("/")
    public String returnHomePage() {
        return "index";
        //return "redirect:/intro";
    }

    @GetMapping("/intro")
    public String returnIntroPage() {
        return "intro";
    }

}
