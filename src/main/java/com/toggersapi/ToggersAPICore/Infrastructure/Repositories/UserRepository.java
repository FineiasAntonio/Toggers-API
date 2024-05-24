package com.toggersapi.ToggersAPICore.Infrastructure.Repositories;

import com.toggersapi.ToggersAPICore.Infrastructure.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "SELECT * FROM users WHERE users.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query(nativeQuery = true, value = "SELECT * FROM users_friends WHERE user_uuid = :uuid")
    Set<User> findUserFriends(@Param("uuid") UUID uuid);

}
