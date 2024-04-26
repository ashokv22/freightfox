package com.freightfox.demo.repository;

import com.freightfox.demo.domain.DistanceResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistanceRepository extends JpaRepository<DistanceResponse, Long> {

    Optional<DistanceResponse> findBySourcePincodeAndDestinationPincode(String fromPincode, String toPincode);

}
