package com.hl95.ssm.dao;

import com.hl95.ssm.entity.SendTplSmsResult;
import java.util.List;

public interface SendTplSmsResultMapper {
    int deleteByPrimaryKey(String rrid);

    int insert(SendTplSmsResult record);

    SendTplSmsResult selectByPrimaryKey(String rrid);

    List<SendTplSmsResult> selectAll();

    int updateByPrimaryKey(SendTplSmsResult record);

    int saveBatch(List<String> rrids);

    int saveOne(String rrids);
}