package com.hl95.ssm.dao;

import com.hl95.ssm.entity.SmsConduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SmsConductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsConduct record);

    SmsConduct selectByPrimaryKey(Integer id);

    List<SmsConduct> selectAll();

    int updateByPrimaryKey(SmsConduct record);

    int saveBatchMsg(List<Map<String, Object>> params);

    List<Map<String,Object>> getMsgsByTaskId(@Param("userId")String userId,@Param("sendId")String sendId);

    List<Map<String,Object>> getMsgs(@Param("userId")String userId,@Param("sendId")String sendId);

    int updateByIds(List<Integer> ids);

    int updateByErrorIds(List<Integer> ids);

    int updateByLinkId(Map<String,Object> params);

    SmsConduct findSmsByLinkId(String id);
}