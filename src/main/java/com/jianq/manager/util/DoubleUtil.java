/**	
 * 
 * <br>
 * Package: com.jianq.spm.util <br>
 * FileName: DoubleUtil.java <br>
 * <br>
 * @version
 * @author hp
 * @created 2017-4-20
 */

package com.jianq.manager.util;

import java.text.DecimalFormat;

/**
 * 
 * @author hp
 * @created 2017-4-20
 */
public class DoubleUtil {

	/**
	 * 把double类型的金额转换成字符串类型的，保留2位小数，且三位一撇
	 * 
	 * @param amount
	 * @return
	 */
	public static String parseDoubleToStrAmount(Double amount) {
		if (null == amount) {
			return "0.00";
		}
		try {
			DecimalFormat df = new DecimalFormat("#,###.00");
			return df.format(amount);
		} catch (Exception e) {
			e.printStackTrace();
			return amount.toString();
		}
	}

	public static void main(String[] args) {
		System.out.println(parseDoubleToStrAmount(122.21));
		System.out.println(parseDoubleToStrAmount(1222222.2221));
		System.out.println(parseDoubleToStrAmount(122d));
		System.out.println(parseDoubleToStrAmount(12222222.21));
	}

}
