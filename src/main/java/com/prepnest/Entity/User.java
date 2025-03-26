package com.prepnest.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_details")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Integer score;

    public User() {
        this.score = 0;
    }
}

