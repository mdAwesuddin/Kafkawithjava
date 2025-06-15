package com.awesuddin.cab_book_driver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CabLocationService {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

//    public boolean updateLocation(String location) {
//        kafkaTemplate.send("cab-location", location);
//        return true;
//    }
public boolean updateLocation(String driverName, String location) {
    // Decide partition based on location
    int partition = location.equalsIgnoreCase("north") ? 0 : 1;

    // Create the message value (can be a JSON string or POJO)
    String message = String.format("{\"name\":\"%s\",\"location\":\"%s\"}", driverName, location);

    // Send to the specified partition
    kafkaTemplate.send("cab-location", partition, "location-update", message);

    return true;
}
}
