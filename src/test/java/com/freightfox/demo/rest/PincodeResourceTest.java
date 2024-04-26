package com.freightfox.demo.rest;

import com.freightfox.demo.domain.DistanceResponse;
import com.freightfox.demo.service.PincodeService;
import com.google.maps.errors.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PincodeResourceTest {

    @Mock
    private PincodeService pincodeService;

    @InjectMocks
    private PincodeResource pincodeResource;

    @Test
    public void testGetDistanceBetweenPincodes() throws IOException, InterruptedException, ApiException {

        // Mock input parameters
        String sourcePincode = "sourcePincode";
        String destinationPincode = "destinationPincode";

        // Mock DistanceResponse
        DistanceResponse distanceResponse = new DistanceResponse();
        distanceResponse.setDistance("100 km");
        distanceResponse.setDuration("2 hours");

        // Mock service method
        when(pincodeService.getDistanceBetweenPincodes(sourcePincode, destinationPincode)).thenReturn(distanceResponse);

        // Call the resource method
        ResponseEntity<DistanceResponse> responseEntity = pincodeResource.getDistanceBetweenPincodes(sourcePincode, destinationPincode);
        // Verify response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(distanceResponse, responseEntity.getBody());
    }
}
