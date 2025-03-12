package com.managment.api.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.managment.api.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email=?1 AND u.password=?2")
    List<User> findByUsernameAndPassword(String email, String clave);

    @Query("select u from User u where u.email=?1")
    User findbyEmail(String email);

}

