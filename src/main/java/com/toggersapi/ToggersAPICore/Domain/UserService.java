package com.toggersapi.ToggersAPICore.Domain;

import com.toggersapi.ToggersAPICore.Infrastructure.DTO.RegisterRequest;
import com.toggersapi.ToggersAPICore.Infrastructure.Entities.Role;
import com.toggersapi.ToggersAPICore.Infrastructure.Entities.User;
import com.toggersapi.ToggersAPICore.Infrastructure.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passEnconder;

    public Map<UUID, User> getAll() {
        return repository.findAll().stream().collect(Collectors.toMap(User::getUuid, user -> user));
    }

    public User getById(UUID uuid) {
        return repository.findById(uuid).orElseThrow(() -> new NoSuchElementException("User hasn't been found"));
    }

    public Map<UUID, User> getUserFriends() {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findUserFriends(authenticatedUser.getUuid()).stream().collect(Collectors.toUnmodifiableMap(User::getUuid, user -> user));
    }

    public User registerNewUser(RegisterRequest request) {

        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException();
        }

        return repository.save(
                User.builder()
                        .username(request.username())
                        .email(request.email())
                        .role(Role.USER)
                        .password(passEnconder.encode(request.password())).build()
        );

    }

}
