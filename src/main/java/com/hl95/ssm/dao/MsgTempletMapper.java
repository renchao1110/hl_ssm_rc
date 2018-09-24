package com.hl95.ssm.dao;

import com.hl95.ssm.entity.MsgTemplet;
import java.util.List;
import java.util.Map;

public interface MsgTempletMapper {
    int deleteByPrimaryKey(String tpl_id);

    int insert(MsgTemplet record);

    MsgTemplet selectByPrimaryKey(String tpl_id);

    List<MsgTemplet> selectAll();

    int updateByParams(Map<String,Object> params);

    int saveMsgTemplet(Map<String,Object> params);

    List<MsgTemplet> getManyMsgTemplets();

}