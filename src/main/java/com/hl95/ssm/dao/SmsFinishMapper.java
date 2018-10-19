package com.hl95.ssm.dao;

import com.hl95.ssm.entity.SmsFinish;
import java.util.List;

public interface SmsFinishMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsFinish record);

    SmsFinish selectByPrimaryKey(Integer id);

    List<SmsFinish> selectAll();

    int updateByPrimaryKey(SmsFinish record);
}