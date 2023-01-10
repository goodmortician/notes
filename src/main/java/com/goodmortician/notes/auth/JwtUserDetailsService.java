package com.goodmortician.notes.auth;

import com.goodmortician.notes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor 
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        com.goodmortician.notes.domain.User user = userRepository.findByLogin(login);
        if (user != null) {
            return new User(user.getLogin(), user.getPassword(), user.getRoles());
        } else {
            throw new UsernameNotFoundException("User not found with login: " + login);
        }
    }
}

