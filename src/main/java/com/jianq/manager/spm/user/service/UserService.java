package com.jianq.manager.spm.user.service;


import com.jianq.manager.spm.user.entity.User;

import java.util.List;

public interface UserService {

    /**
     * 根据id获取用户信息
     *
     * @param userId
     * @return
     */
    User selectById(Integer userId);

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

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    Integer insertUser(User user);

    /**
     * 获取用户信息
     *
     * @param userCode 为空则从session中获取
     * @return
     */
    User getUser(String userCode);

    /**
     * @param userCode
     * @return
     */
    String getSessionOrgId(String userCode);

}
