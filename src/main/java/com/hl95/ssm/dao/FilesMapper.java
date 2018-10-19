package com.hl95.ssm.dao;

import com.hl95.ssm.entity.Files;
import java.util.List;
import java.util.Map;

public interface FilesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Files record);

    Files selectByPrimaryKey(Integer id);

    List<Files> selectAll();

    int updateByPrimaryKey(Files record);

    int save(Map<String,String> map);

    int count(String fileName);

    int saveBatch(List<Map<String,Object>> list);
}