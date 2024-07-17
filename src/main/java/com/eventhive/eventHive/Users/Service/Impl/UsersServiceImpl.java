package com.eventhive.eventHive.Users.Service.Impl;

import com.eventhive.eventHive.Auth.helper.Claims;
import com.eventhive.eventHive.Exceptions.EmailAlreadyExistException;
import com.eventhive.eventHive.Exceptions.ReferralNotFoundException;
import com.eventhive.eventHive.PointHistory.Entity.PointHistory;
import com.eventhive.eventHive.PointHistory.Repository.PointHistoryRepository;
import com.eventhive.eventHive.PointHistory.Service.PointHistoryService;
import com.eventhive.eventHive.Referral.Entity.Referral;
import com.eventhive.eventHive.Referral.Repository.ReferralRepository;
import com.eventhive.eventHive.Referral.Service.ReferralService;
import com.eventhive.eventHive.Users.Entity.Users;
import com.eventhive.eventHive.Users.Repository.UsersRepository;
import com.eventhive.eventHive.Users.Service.UsersService;
import com.eventhive.eventHive.Users.dto.RegisterReqDto;
import com.eventhive.eventHive.Users.dto.RegisterRespDto;
import com.eventhive.eventHive.Users.dto.UserProfileDto;
import com.eventhive.eventHive.Voucher.Service.VoucherService;
import com.eventhive.eventHive.utils.ReferralCodeGenerator;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@Log
public class UsersServiceImpl implements UsersService {
    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final PointHistoryService pointHistoryService;
    private final ReferralService referralService;
    private final VoucherService voucherService;

    public UsersServiceImpl(UsersRepository repository, PasswordEncoder passwordEncoder, PointHistoryService pointHistoryService, ReferralService referralService, VoucherService voucherService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.pointHistoryService = pointHistoryService;
        this.referralService = referralService;
        this.voucherService = voucherService;
    }

    @Override
    public Users getUserById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new RuntimeException("User is not found"));
    }

    @Transactional
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

        //check dto if referral code is provided
        if (user.getReferralCode() != null && !user.getReferralCode().isEmpty() && newUser.getRole() == Users.UserRole.CUSTOMER){
            Users referringUser = repository.findByReferralCode(user.getReferralCode()).orElseThrow(() -> new ReferralNotFoundException("Referral code is not found"));
            //Create referral record
            referralService.createReferral(referringUser, newUser);
            //Create a history point record
            pointHistoryService.awardsPoints(referringUser, 10000);
            newUser.setIsUsedReferralBenefit(true);
        }
        //Ensure referral code only set to customer role
        if (newUser.getRole() == Users.UserRole.CUSTOMER){
            newUser.setReferralCode(ReferralCodeGenerator.generateReferralCode());
        } else {
            newUser.setReferralCode(null);
        }

        Users savedUser = repository.save(newUser);
        return RegisterRespDto.fromEntity(savedUser);
    }

    @Override
    public UserProfileDto getProfile() {
        var claims =Claims.getClaimsFromJwt();
        log.info("CLAIMS = " + claims);
        Long currentUserId =(Long) claims.get("userId");

        Users currentUser = repository.findById(currentUserId).orElseThrow(() -> new UsernameNotFoundException("Id is not found"));
        UserProfileDto profileDto = UserProfileDto.fromEntity(currentUser);
        profileDto.setId(currentUser.getId());
        profileDto.setPoints(pointHistoryService.totalActivePoints(currentUserId));
        log.info("PROFILE DTO = " + profileDto);
        return profileDto;
    }

    @Override
    public Users findById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("Username is not exist"));
    }
}
