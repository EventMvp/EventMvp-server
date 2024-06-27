package com.eventhive.eventHive.Users.Service.Impl;

import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Users.Repository.UsersRepository;
import com.eventhive.eventHive.Users.Service.UsersService;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository repository;

    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public Users getUserById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new RuntimeException("User is not found"));
    }
}
