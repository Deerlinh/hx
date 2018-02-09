package com.jianq.manager.spm.user.dao;


import com.jianq.manager.spm.user.entity.User;

import java.util.List;

public interface UserDao {
    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    /**
     * 根据条件查询用户信息
     *
     * @param user
     * @return
     */
    User selectByCondition(User user);

    /**
     * 根据组织id查所有用户
     *
     * @param orgId
     * @return
     */
    List<User> selectByOrgId(String orgId);
}