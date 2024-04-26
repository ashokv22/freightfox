package com.freightfox.demo.rest;

import com.freightfox.demo.domain.DistanceResponse;
import com.freightfox.demo.service.PincodeService;
import com.google.maps.errors.ApiException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/pincode")
@Slf4j
@AllArgsConstructor
public class PincodeResource {

    private PincodeService pincodeService;

    @GetMapping("/distance")
    public ResponseEntity<DistanceResponse> getDistanceBetweenPincodes(@RequestParam String source, @RequestParam String destination) throws IOException, InterruptedException, ApiException {
        // Call service to get distance information and return response
        DistanceResponse response = pincodeService.getDistanceBetweenPincodes(source, destination);
        return ResponseEntity.ok(response);
    }
}


