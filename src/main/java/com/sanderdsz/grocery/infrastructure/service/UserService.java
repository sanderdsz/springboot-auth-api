package com.sanderdsz.grocery.infrastructure.service;

import com.sanderdsz.grocery.domain.model.SecurityUser;
import com.sanderdsz.grocery.domain.model.User;
import com.sanderdsz.grocery.domain.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("E-mail not found: " + username));
    }

    public User save(User user) {

        Optional<User> userExists = userRepository.findByEmail(user.getEmail());

        if (userExists.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail is already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }


}
