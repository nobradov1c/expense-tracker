package com.expensetracker.main.domain.user.service;

import com.expensetracker.main.domain.user.repository.UserRepository;
import com.expensetracker.main.security.MyUserDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new MyUserDetails(
                userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }

}
