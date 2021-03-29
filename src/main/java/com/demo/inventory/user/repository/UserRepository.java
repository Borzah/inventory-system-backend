package com.demo.inventory.user.repository;

import com.demo.inventory.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUsername(String username);

    User findUserByUserId(Long userId);

    void deleteByUserId(Long userId);

    User findUserByUsername(String username);
}
