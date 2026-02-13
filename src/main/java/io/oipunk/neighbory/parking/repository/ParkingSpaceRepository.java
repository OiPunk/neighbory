package io.oipunk.neighbory.parking.repository;

import io.oipunk.neighbory.parking.entity.ParkingSpace;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

    Optional<ParkingSpace> findByCode(String code);
}
