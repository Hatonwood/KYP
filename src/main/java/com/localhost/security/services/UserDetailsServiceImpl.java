package com.localhost.security.services;

import com.localhost.entity.User;
import com.localhost.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()) {
            User existingUser = user.get();
            if (existingUser.isEnabled())
                return UserPrinciple.build(existingUser);
        }
        throw new UsernameNotFoundException("Username " + username + " not found");
    }

}
