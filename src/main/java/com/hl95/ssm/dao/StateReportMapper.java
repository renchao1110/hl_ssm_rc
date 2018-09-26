package com.hl95.ssm.dao;

import com.hl95.ssm.entity.StateReport;
import java.util.List;

public interface StateReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StateReport record);

    StateReport selectByPrimaryKey(Integer id);

    List<StateReport> selectAll();

    int updateByPrimaryKey(StateReport record);
}