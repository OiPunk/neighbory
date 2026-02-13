package io.oipunk.propertylab.estate.repository;

import io.oipunk.propertylab.estate.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query("select count(u) from Unit u where u.building.estate.id = :estateId")
    long countByEstateId(@Param("estateId") Long estateId);
}
