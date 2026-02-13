package io.oipunk.neighbory.estate.repository;

import io.oipunk.neighbory.estate.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    long countByEstateId(Long estateId);
}
