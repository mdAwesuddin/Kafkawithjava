package com.awesuddin.cab_book_driver.service;

import com.awesuddin.cab_book_driver.entity.Driver;
import com.awesuddin.cab_book_driver.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DriverDetailsService implements UserDetailsService {

    private final DriverRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Driver driver = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Driver not found"));

        return new org.springframework.security.core.userdetails.User(
                driver.getUsername(),
                driver.getPassword(),
                new ArrayList<>()
        );
    }
}
