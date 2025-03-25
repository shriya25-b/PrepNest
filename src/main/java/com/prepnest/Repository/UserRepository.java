package com.prepnest.Repository;

import com.prepnest.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    public boolean existsByEmail(String email);
}
