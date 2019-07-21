package com.localhost.service;

import com.localhost.entity.User;

import java.util.Optional;

public interface UserService {

    public Optional<User> findByUsername(String username);

}
