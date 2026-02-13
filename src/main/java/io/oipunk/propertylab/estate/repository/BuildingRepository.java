package io.oipunk.propertylab.estate.repository;

import io.oipunk.propertylab.estate.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    long countByEstateId(Long estateId);
}
