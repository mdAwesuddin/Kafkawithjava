package com.awesuddin.cab_book_driver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationUpdateRequest {
    private String driverName;
    private String location;
}
