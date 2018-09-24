package com.hl95.ssm.dao;

import com.hl95.ssm.entity.Address;
import java.util.List;

public interface AddressMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    Address selectByPrimaryKey(Integer id);

    List<Address> selectAll();

    int updateByPrimaryKey(Address record);

    int getCountIp(String ip);
}