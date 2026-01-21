package com.aid.daily_expenditure.User;

import com.aid.daily_expenditure.Security.MyValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void signUp(SignUpInput input) throws MyValidationException {
        List<ObjectError> errors = new java.util.ArrayList<>();

        if (userRepository.existsById(input.getUserId())) {
            errors.add(new ObjectError("User ID", "This User ID is used by others."));
        }
        if (userRepository.existsByEmail(input.getEmail())) {
            errors.add(new ObjectError("Email Address", "This Email Address is used by others."));
        }
        if (!errors.isEmpty()) {
            throw new MyValidationException(errors);
        }

        User newUser = new User(input);
        userRepository.save(newUser);
    }
}
