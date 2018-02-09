package com.jianq.manager.constant;

/**
 * service层的返回值常量 <br>
 * 
 * @version 1.0.0
 * @created 2017-7-17 下午4:57:56
 * @author king
 */
public class ConstantServiceReturn {

	/**
	 * 成功(更新、插入、删除)
	 */
	public static final int SUCCESS = 1;
	/**
	 * 失败(更新、插入、删除)
	 */
	public static final int FAILURE = 0;

	/**
	 * 数据库异常
	 */
	public static final int DATABASE_EXCEPTION = -1;

	/**
	 * 有参数值为空
	 */
	public static final int PARAM_VALUE_EMPTY = -2;

	/**
	 * 未登录
	 */
	public static final int UNLOGIN = -500;

	public static String getStatusDesc(int statusCode) {
		String result = "";
		switch (statusCode) {
		case SUCCESS:
			result = "成功";
			break;
		case FAILURE:
			result = "失败";
			break;
		case DATABASE_EXCEPTION:
			result = "数据库异常";
			break;
		case PARAM_VALUE_EMPTY:
			result = "参数值不能为空";
			break;
		case UNLOGIN:
			result = "请先登录";
			break;
		default:
			result = "异常";
			break;
		}
		return result;
	}

}
