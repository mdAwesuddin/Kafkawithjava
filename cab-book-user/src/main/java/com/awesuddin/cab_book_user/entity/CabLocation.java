package com.awesuddin.cab_book_user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cab_locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String driverName;

    private String location;

    private int partition;

    private LocalDateTime receivedAt;
}
