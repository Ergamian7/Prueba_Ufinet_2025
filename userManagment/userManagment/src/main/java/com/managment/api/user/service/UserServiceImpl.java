package com.managment.api.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.managment.api.user.entity.User;
import com.managment.api.user.repository.UserRepository;
import com.managment.api.user.response.LoginResponse;
import com.managment.api.user.response.UserResponse;
import com.managment.api.user.security.JwtTokenProvider;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User add(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User userObject = userRepository.getById(user.getId());
        BeanUtils.copyProperties(user, userObject);
        return userRepository.save(userObject);
    }

    @Override
    public void delete(User user) {
        User usuarioObject = userRepository.getById(user.getId());
        userRepository.delete(usuarioObject);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findbyEmail(email);
    }

    @Override
    public boolean checkPassword(User user, String password) {
        User userBd = userRepository.findbyEmail(user.getEmail());
        if (userBd == null) {
            return false;
        }

        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public LoginResponse getTokenByUsername(String username) {
        User user = userRepository.findbyEmail(username);
        if (user == null) {
            return LoginResponse.builder()
                    .message("Username or password invalid!")
                    .status(false)
                    .data(null)
                    .build();
            
        }
        
        UserResponse response = UserResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .tokeString(tokenProvider.generateToken(username))
                .build();

        return LoginResponse.builder()
                .message("Login successful!")
                .status(true)
                .data(response)
                .build();
    }

}
