package com.jianq.manager.spm.cargo.dao;

import com.jianq.manager.spm.cargo.entity.Cargo;

import java.util.List;

public interface CargoMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Cargo record);

    Cargo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cargo record);

    List<Cargo> listCargo(Cargo cargo);
}