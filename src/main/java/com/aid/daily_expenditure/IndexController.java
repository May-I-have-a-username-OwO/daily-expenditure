package com.aid.daily_expenditure;

import com.aid.daily_expenditure.User.SignUpInput;
import com.aid.daily_expenditure.User.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
