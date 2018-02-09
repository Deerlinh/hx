package com.jianq.manager.util;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.util.CollectionUtils;

import com.jianq.auth.shiro.clinet.constants.AuthConstant;
import com.jianq.manager.spm.user.entity.User;

/**
 * 权限工具类
 * 
 * @author liujian
 */
@SuppressWarnings("unchecked")
public class AuthorityUtil {

	public static User getSessionUser() {
		Object userObj = SecurityUtils.getSubject().getSession()
				.getAttribute(AuthConstant.USER_SESSION_KEY);
		if (null == userObj) {
			return null;
		}
		if (userObj instanceof Map) {
			Map<String, Object> userMap = (Map<String, Object>) userObj;
			User user = null;
			if (!CollectionUtils.isEmpty(userMap)) {
				try {
					user = new User();
					user.setUserId(Integer
							.valueOf(userMap.get("id").toString()));
					if (StringUtil.isNotEmpty(userMap.get("realName"))) {
						user.setUserName(String.valueOf(userMap.get("realName")
								.toString()));
					}
					if (StringUtil.isNotEmpty(userMap.get("username"))) {
						user.setUserCode(String.valueOf(userMap.get("username")
								.toString()));
					}
					if (StringUtil.isNotEmpty(userMap.get("deptName"))) {
						user.setOrgName(String.valueOf(userMap.get("deptName")
								.toString()));
					} else {
						user.setOrgName("");
					}
				} catch (Exception e) {
					throw new RuntimeException("方法 getSessionUser 出错，", e);
				}
				SecurityUtils.getSubject().getSession()
						.setAttribute(AuthConstant.USER_SESSION_KEY, user);
			}

			return user;
		}

		return (User) userObj;

	}

	public static Integer getSessionUserId() {
		User user = getSessionUser();
		if (null == user) {
			return null;
		}
		return user.getUserId();
	}

	public static String getSessionUserCode() {
		User user = getSessionUser();
		if (null == user) {
			return null;
		}
		return user.getUserCode();
	}

}
