package com.eventhive.eventHive.Users.Repository;

import com.eventhive.eventHive.Users.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
