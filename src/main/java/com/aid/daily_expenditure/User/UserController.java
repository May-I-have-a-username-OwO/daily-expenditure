package com.aid.daily_expenditure.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

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

 */

    @GetMapping("/signUp")
    public String returnSignUpPage() {
        return "signUp";
    }

    @PostMapping(value = "/signUpValidation", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> signUpValidation(@Valid @ModelAttribute SignUpInput signUpInput, HttpServletRequest request) {
        userService.signUp(signUpInput);
        userService.signInAfterSignUp(signUpInput.getUserId(), request);
        //return "redirect:/";
        return new ResponseEntity<>("ok", HttpStatus.CREATED);
    }
/*
    @PostMapping(value = "/signUp", consumes = "application/x-www-form-urlencoded")
    public String signUp(@ModelAttribute User user) {
        userService.addUser(user);
        //return "idk";
        return null;
    }
*/
}
