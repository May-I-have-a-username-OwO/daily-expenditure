package com.aid.daily_expenditure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /*public void addUser(User user) {
        userRepository.save(user);
    }

    public boolean idUsed(String id) {
        return userRepository.existsById(id);
    }

    public boolean emailUsed(String email) {
        return userRepository.existsByEmail(email);
    }*/

}
