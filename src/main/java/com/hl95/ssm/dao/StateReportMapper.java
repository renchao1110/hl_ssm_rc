package com.hl95.ssm.dao;

import com.hl95.ssm.entity.StateReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StateReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StateReport record);

    StateReport selectByPrimaryKey(Integer id);

    List<StateReport> selectAll();

    int updateByPrimaryKey(StateReport record);

    List<Map<String,Object>> getReports();

    int updateReports(@Param("list") List<Map<String,Object>> list);

    int saveReport(Map<String, Object> params);

    int updateById(Map<String, Object> params);
}