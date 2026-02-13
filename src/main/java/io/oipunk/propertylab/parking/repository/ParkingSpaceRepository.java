package io.oipunk.propertylab.parking.repository;

import io.oipunk.propertylab.parking.entity.ParkingSpace;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

    Optional<ParkingSpace> findByCode(String code);
}
