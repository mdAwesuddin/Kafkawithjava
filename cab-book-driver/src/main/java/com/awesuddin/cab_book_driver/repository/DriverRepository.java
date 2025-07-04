package com.awesuddin.cab_book_driver.repository;

import com.awesuddin.cab_book_driver.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findByUsername(String username);
}
