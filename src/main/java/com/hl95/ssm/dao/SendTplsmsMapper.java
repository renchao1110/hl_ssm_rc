package com.hl95.ssm.dao;

import com.hl95.ssm.entity.SendTplsms;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SendTplsmsMapper {
    int deleteByPrimaryKey(Integer id);

    int saveOne(SendTplsms sendTplsms);

    SendTplsms selectByPrimaryKey(Integer id);

    List<SendTplsms> selectAll();

    int updateByPrimaryKey(SendTplsms record);

    int saveBatch(@Param("list") List<SendTplsms> list);

    int saveBatchMap(@Param("list") List<Map<String,Object> > list);

    int deleteByrrids(List<String> rridsOk);

    int updateByOK(List<String> rridsOk);

    int updateByError(List<String> rridsError);

}