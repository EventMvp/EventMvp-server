package com.eventhive.eventHive.Users.Service.Impl;

import com.eventhive.eventHive.Exceptions.EmailAlreadyExistException;
import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Users.Repository.UsersRepository;
import com.eventhive.eventHive.Users.Service.UsersService;
import com.eventhive.eventHive.Users.dto.RegisterReqDto;
import com.eventhive.eventHive.Users.dto.RegisterRespDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users getUserById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new RuntimeException("User is not found"));
    }

    @Override
    public RegisterRespDto register(RegisterReqDto user) {
        if (repository.findByEmail(user.getEmail()).isPresent()){
            throw new EmailAlreadyExistException("Email already exist");
        }
        if (!Objects.equals(user.getPassword(), user.getPasswordMatch())){
            throw new RuntimeException("Password is wrong");
        }
        Users newUser = user.toEntity();
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Users savedUser = repository.save(newUser);
        return RegisterRespDto.fromEntity(savedUser);
    }
}
