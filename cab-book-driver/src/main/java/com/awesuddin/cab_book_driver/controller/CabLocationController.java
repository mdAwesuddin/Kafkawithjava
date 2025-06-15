package com.awesuddin.cab_book_driver.controller;

import com.awesuddin.cab_book_driver.dto.LocationUpdateRequest;
import com.awesuddin.cab_book_driver.service.CabLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/location")
public class CabLocationController {

    @Autowired
    private CabLocationService cabLocationService;

    //Business Logic



    @PutMapping
//    public ResponseEntity updateLocation() throws InterruptedException {
//
//        int range = 100;
//        while (range > 0) {
//            cabLocationService.updateLocation(Math.random() + " , " + Math.random());
//            Thread.sleep(1000);
//            range --;
//        }
//
//        return new ResponseEntity<>(Map.of("message", "Location Updated")
//                , HttpStatus.OK);
//    }

    public ResponseEntity<?> updateLocation(@RequestBody LocationUpdateRequest request) {
        boolean success = cabLocationService.updateLocation(request.getDriverName(), request.getLocation());
        return new ResponseEntity<>(Map.of("message", success ? "Location updated" : "Failed"), HttpStatus.OK);
    }
}