package com.demo.inventory.user.repository;

import com.demo.inventory.security.DbRole;
import com.demo.inventory.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.role = 0")
    List<User> findAllUsersWithRoleUser();

    User findUserByUserId(Long userId);

    void deleteByUserId(Long userId);

    User findUserByUsername(String username);
}
