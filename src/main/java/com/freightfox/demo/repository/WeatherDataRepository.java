package com.freightfox.demo.repository;

import com.freightfox.demo.domain.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    WeatherData findByPincode(String pincode);

}
