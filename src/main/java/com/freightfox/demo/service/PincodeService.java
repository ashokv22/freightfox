package com.freightfox.demo.service;

import com.freightfox.demo.domain.DistanceResponse;
import com.google.maps.errors.ApiException;

import java.io.IOException;

public interface PincodeService {

    DistanceResponse getDistanceBetweenPincodes(String source, String destination) throws IOException, InterruptedException, ApiException;

}
