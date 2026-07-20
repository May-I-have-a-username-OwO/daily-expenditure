package com.aid.daily_expenditure.User;

import com.aid.daily_expenditure.Security.MyValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;


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

    public void signInAfterSignUp(String userId, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession();
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());
    }
}
