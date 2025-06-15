package com.awesuddin.cab_book_user.repository;

import com.awesuddin.cab_book_user.entity.CabLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CabLocationRepository extends JpaRepository<CabLocation, Long> {
}