package com.freightfox.demo.service.impl;

import com.freightfox.demo.domain.DistanceResponse;
import com.freightfox.demo.repository.DistanceRepository;
import com.freightfox.demo.service.PincodeService;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class PincodeServiceImpl implements PincodeService {

    @Value("${apis.google.maps.api-key}")
    private String googleMapsApiKey;

    private final CacheManager cacheManager;

    private final DistanceRepository distanceRepository;


    public PincodeServiceImpl(CacheManager cacheManager, DistanceRepository distanceRepository) {
        this.cacheManager = cacheManager;
        this.distanceRepository = distanceRepository;
    }

    public DistanceResponse getDistanceBetweenPincodes(String fromPincode, String toPincode) throws IOException, InterruptedException, ApiException {
        log.info("Getting distance between {} and {}", fromPincode, toPincode);
        GeoApiContext context = new GeoApiContext.Builder().apiKey(googleMapsApiKey).build();

        // Check if data exists in cache
        Cache cache = cacheManager.getCache("pincode-distances");
        String cacheKey = fromPincode + "-" + toPincode;
        Cache.ValueWrapper cachedData = null;
        if (cache != null) {
            cachedData = cache.get(cacheKey);
            if (cachedData != null) {
                // Data exists in cache, return cached response
                log.info("Data found in Cache for {}", cacheKey);
                return (DistanceResponse) cachedData.get();
            } else {
                // Data not found in cache, check in database
                Optional<DistanceResponse> distanceResponse = distanceRepository.findBySourcePincodeAndDestinationPincode(fromPincode, toPincode);
                if (distanceResponse.isPresent()) {
                    log.info("Data found in database for {}", cacheKey);
                    return distanceResponse.get();
                }
            }
        }

        /* Call Google Maps API if not cached */
        log.info("Data not found in cache, calling Google Maps API...");
        DistanceResponse distanceResponse = getDistanceResponse(fromPincode, toPincode, context);

        cache.put(cacheKey, distanceResponse);
        return distanceResponse;
    }

    private DistanceResponse getDistanceResponse(String fromPincode, String toPincode, GeoApiContext context) throws ApiException, InterruptedException, IOException {
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
        req.origins(fromPincode);
        req.destinations(toPincode);
        DistanceMatrix result = null;
        result = req.await();

        String distanceText = result.rows[0].elements[0].distance.humanReadable;
        String durationText = result.rows[0].elements[0].duration.humanReadable;

        DistanceResponse distanceResponse = new DistanceResponse();
        distanceResponse.setDistance(distanceText);
        distanceResponse.setSourcePincode(fromPincode);
        distanceResponse.setDestinationPincode(toPincode);
        distanceResponse.setDuration(durationText);
        distanceRepository.save(distanceResponse);
        return distanceResponse;
    }
}
