package com.prepnest.Service;

import com.prepnest.Entity.User;
import com.prepnest.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void updateUserScore(Long userId, int score) {
        User user = userRepository.findById(Math.toIntExact(userId)).orElse(null);
        if (user != null) {
            user.setScore(score);
            userRepository.save(user);
        }
    }
}
