package com.jianq.manager.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayUtil {
	/**
	 * 求sourceList对targetList的差集<br/>
	 * 即sourceList中有，但targetList中没有的
	 * 
	 * @param sourceList
	 * @param targetList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List differenceSet(List sourceList, List targetList) {
		List list = new ArrayList(Arrays.asList(new Object[sourceList.size()]));
		Collections.copy(list, sourceList);
		list.removeAll(targetList);
		return list;
	}

	/**
	 * 求2个集合的交集
	 * 
	 * @param ls
	 * @param ls2
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List intersect(List ls, List ls2) {
		List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
		Collections.copy(list, ls);
		list.retainAll(ls2);
		return list;
	}

	/**
	 * 求2个集合的并集
	 * 
	 * @param ls
	 * @param ls2
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List union(List ls, List ls2) {
		List list = new ArrayList(Arrays.asList(new Object[ls.size()]));
		Collections.copy(list, ls);// 将ls的值拷贝一份到list中
		list.removeAll(ls2);
		list.addAll(ls2);
		return list;
	}

	/**
	 * 把集合变成字符串，中间用英文逗号隔开
	 * 
	 * @param list
	 *            [1,2,3]
	 * @return "1,2,3"
	 */
	public static String parseListToString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(",");
			sb.append(str);
		}
		// 去掉开始的 逗号
		return sb.substring(1);
	}
}
