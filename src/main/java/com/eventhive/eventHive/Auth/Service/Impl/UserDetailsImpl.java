package com.eventhive.eventHive.Auth.Service.Impl;

import com.eventhive.eventHive.Auth.entity.UserAuth;
import com.eventhive.eventHive.Users.Repository.UsersRepository;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log
@Service
public class UserDetailsImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    public UserDetailsImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = usersRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserAuth(user);
    }
}
