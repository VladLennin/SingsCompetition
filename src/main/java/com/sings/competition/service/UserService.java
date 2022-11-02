package com.sings.competition.service;


import com.sings.competition.domain.Role;
import com.sings.competition.domain.User;
import com.sings.competition.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleStatus;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) throws Exception {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new Exception("Користувач з таким логіном вже існує!");
        }
        user.setActive(true);
        user.getRoles().add(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
