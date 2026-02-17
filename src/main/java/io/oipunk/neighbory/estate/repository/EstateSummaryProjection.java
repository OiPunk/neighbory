package io.oipunk.neighbory.estate.repository;

/**
 * Projection for estate list aggregate queries.
 * Returns summary statistics in a single SQL query to avoid N+1 access patterns.
 */
public interface EstateSummaryProjection {

    Long getId();

    String getCode();

    String getName();

    String getAddress();

    String getRemark();

    long getBuildingCount();

    long getUnitCount();
}
