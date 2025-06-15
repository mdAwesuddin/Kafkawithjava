package com.awesuddin.cab_book_user.service;


import com.awesuddin.cab_book_user.entity.CabLocation;
import com.awesuddin.cab_book_user.service.CabLocationBatchService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocationService {

//    @KafkaListener(topics = "cab-location", groupId = "user-group")
//    public void cabLocation(String location,
//                            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
//        System.out.println("Partition: " + partition + " | Location: " + location);
//    }
@Autowired
private CabLocationBatchService batchService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "cab-location", groupId = "user-group")
    public void cabLocation(String message,
                            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        try {
            JsonNode json = objectMapper.readTree(message);
            String name = json.get("name").asText();
            String location = json.get("location").asText();

            CabLocation cabLocation = CabLocation.builder()
                    .driverName(name)
                    .location(location)
                    .partition(partition)
                    .receivedAt(LocalDateTime.now())
                    .build();

            batchService.addToBuffer(cabLocation);

        } catch (Exception e) {
            System.err.println("❌ Failed to process message: " + message);
        }
    }
}
