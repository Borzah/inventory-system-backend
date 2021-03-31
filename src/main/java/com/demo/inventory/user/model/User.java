package com.demo.inventory.user.model;

import com.demo.inventory.security.DbRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "App_user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private DbRole role;
    @Column(name = "date_registered")
    private Timestamp dateRegistered;

    public User(String username, String password, DbRole role, Timestamp dateRegistered) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.dateRegistered = dateRegistered;
    }
}
