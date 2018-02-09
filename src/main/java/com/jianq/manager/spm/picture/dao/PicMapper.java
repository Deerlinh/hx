package com.jianq.manager.spm.picture.dao;


import com.jianq.manager.spm.picture.entity.Pic;

public interface PicMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Pic record);

    Pic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pic pic);
}