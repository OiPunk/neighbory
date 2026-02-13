package io.oipunk.neighbory.estate.repository;

import io.oipunk.neighbory.estate.entity.Estate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EstateRepository extends JpaRepository<Estate, Long> {

    boolean existsByCode(String code);

    Optional<Estate> findByCode(String code);

    @Query("""
            select e.id as id,
                   e.code as code,
                   e.name as name,
                   e.address as address,
                   e.remark as remark,
                   count(distinct b.id) as buildingCount,
                   count(u.id) as unitCount
              from Estate e
              left join e.buildings b
              left join b.units u
             group by e.id, e.code, e.name, e.address, e.remark
             order by e.id
            """)
    List<EstateSummaryProjection> findAllSummary();

}
