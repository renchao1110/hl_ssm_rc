package com.hl95.ssm.dao;

import com.hl95.ssm.entity.UpReport;
import java.util.List;
import java.util.Map;

public interface UpReportMapper {
    int deleteByPrimaryKey(String id);

    int insert(UpReport record);

    UpReport selectByPrimaryKey(String id);

    List<UpReport> selectAll();

    int updateByPrimaryKey(UpReport record);

    int saveUpReport(Map<String,Object> params);

    List<Map<String,Object>> getUpReports();

    int updateUpReports(List<Map<String,Object>> list);

    int updateById(Map<String, Object> params);
}