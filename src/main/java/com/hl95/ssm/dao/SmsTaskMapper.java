package com.hl95.ssm.dao;

import com.hl95.ssm.entity.SmsTask;
import java.util.List;
import java.util.Map;

public interface SmsTaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsTask record);

    SmsTask selectByPrimaryKey(Integer id);

    List<SmsTask> selectAll();

    int updateByPrimaryKey(SmsTask record);

    int saveSmsTask(Map<String,String> params);

    List<SmsTask> getTasks();

    int updateErrorStateById(List<Integer> ids);

    int updateStateById(List<Integer> ids);

    List<Map<String,String>> getCompleteTask();

    int updateCompleteStateById(List<String> ids);
}