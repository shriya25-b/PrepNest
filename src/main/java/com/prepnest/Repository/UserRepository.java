package com.prepnest.Repository;

import com.prepnest.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(@Param("username") String username);
    public boolean existsByEmail(String email);
}
