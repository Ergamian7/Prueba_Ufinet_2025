package com.managment.api.user.service;

import java.util.List;
import java.util.Optional;

import com.managment.api.user.entity.User;
import com.managment.api.user.response.LoginResponse;

public interface UserService {

    public List<User> findAll();

    public Optional<User> findById(Long id);

    public User findByEmail(String email);

    public boolean checkPassword(User user, String password);

    public User add(User usuario);

    public User update(User usuario);

    public void delete(User usuario);

    public LoginResponse getTokenByUsername(String username);

}
