package io.oipunk.neighbory.estate.repository;

/**
 * 楼盘列表聚合查询投影，用于一次 SQL 返回统计字段，避免 N+1 查询。
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
