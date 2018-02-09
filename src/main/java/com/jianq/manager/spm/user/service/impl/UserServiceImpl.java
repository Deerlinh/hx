package com.jianq.manager.spm.user.service.impl;

import com.jianq.manager.spm.user.dao.UserDao;
import com.jianq.manager.spm.user.entity.User;
import com.jianq.manager.spm.user.service.UserService;
import com.jianq.manager.util.AuthorityUtil;
import com.jianq.manager.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    /**
     * 根据id获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public User selectById(Integer userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Override
    public User selectByCondition(User user) {
        return userDao.selectByCondition(user);
    }

    @Override
    public List<User> selectByOrgId(String orgId) {
        return userDao.selectByOrgId(orgId);
    }

    @Override
    public Integer insertUser(User user) {
        return userDao.insertSelective(user);
    }

    @Override
    public User getUser(String userCode) {
//        if (StringUtil.isBlank(userCode)) {
//            // 没有手机号 从session获取
//            userCode = AuthorityUtil.getSessionUserCode();
//            log.info("没有手机号，从session中获取的手机号是：{}", userCode);
//        }
        if (StringUtil.isBlank(userCode)) {
            return null;
        }
        User user = null;
        try {
            user = userDao.selectByCondition(new User(null, userCode));
        } catch (Exception e) {
            log.error("" + e);
        }
        return user;
    }

    @Override
    public String getSessionOrgId(String userCode) {
        String orgId = null;
        User user = getUser(userCode);
        if (user != null) {
            orgId = user.getOrgId();
        }
        return orgId;
    }

}
