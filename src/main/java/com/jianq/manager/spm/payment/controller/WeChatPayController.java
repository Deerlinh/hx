package com.jianq.manager.spm.payment.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jianq.manager.spm.payment.util.WXPay;
import com.jianq.manager.spm.payment.util.WXPayConfig;
import com.jianq.manager.spm.payment.util.WXPayConfigImpl;
import com.jianq.manager.spm.payment.util.WXPayConstants.SignType;
import com.jianq.manager.spm.payment.util.WXPayUtil;
import com.jianq.manager.util.ReturnUtil;
import com.jianq.manager.util.StaticPropertiesUtil;

@RestController
@RequestMapping("/**/wechatpay")
public class WeChatPayController {

	private static final Logger log = LoggerFactory
			.getLogger(WeChatPayController.class);

	/**
	 * 生成预订单
	 * 
	 * @param orderId
	 *            订单号
	 * @param amount
	 *            金额
	 * @return
	 */
	@RequestMapping("advanceOrder")
	public static Object generateOrderInfo(String orderId, String amount,
			HttpServletRequest request) {
		Date now = new Date();
		String time_start = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
		Map<String, String> requestMap = buildRequestData(amount, orderId,
				"192.168.1.2");// getIpAddress(request)
		requestMap.put("time_start", time_start);
		// requestMap.put("time_expire", "201710261800000");

		String notifyUrl = StaticPropertiesUtil
				.getValue("current.server.url.domain")
				+ "/hongxing/app/wechatpay/check";
		WXPayConfig config;
		Map<String, String> result = null;
		try {
			config = WXPayConfigImpl.getInstance();
			WXPay wxPay = new WXPay(config, notifyUrl);
			result = wxPay.unifiedOrder(requestMap);
			result.put("timestamp", (now.getTime() + "").substring(0, 10));

			Map<String, String> resp = buildResponseData(result);
			return ReturnUtil.success("生成预订单成功", resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("{}", e);
		}

		return ReturnUtil.fail("生成预订单失败");
	}

	/**
	 * 该函数适用于商户适用于统一下单等接口，不适用于红包、代金券接口
	 * 
	 * @param reqData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> buildRequestData(String amount,
			String orderId, String ip) {
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("body", "红星美凯龙 - 商品购买");
		reqData.put("attach", "Andy");
		reqData.put("out_trade_no", orderId);
		reqData.put("total_fee", amount);
		reqData.put("spbill_create_ip", ip);
		reqData.put("trade_type", "APP");
		return reqData;
	}

	/**
	 * 构造返回参数
	 * 
	 * @param reqData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> buildResponseData(Map<String, String> map)
			throws Exception {
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("appid", map.get("appid"));
		resp.put("partnerid", WXPayConfigImpl.getInstance().getMchID());
		resp.put("prepayid", map.get("prepay_id"));
		resp.put("package", "Sign=WXPay");
		resp.put("noncestr", map.get("nonce_str"));
		resp.put("timestamp", map.get("timestamp"));
		resp.put("sign", WXPayUtil.generateSignature(resp, WXPayConfigImpl
				.getInstance().getKey(), SignType.MD5));
		return resp;
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100
	 * 
	 * 用户真实IP为： 192.168.1.110
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 构造返回参数
	 * 
	 * @param reqData
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> test(Map<String, String> map)
			throws Exception {
		Map<String, String> resp = new HashMap<String, String>();
		resp.put("appid", "wxa7e44efab0989806");
		resp.put("partnerid", "1460451302");
		resp.put("prepayid", "wx20171026161211f9eea9add10140783315");
		resp.put("package", "Sign=WXPay");
		resp.put("noncestr", "b1b0432ceafb0ce714426e9114852ac7");
		resp.put("timestamp", "1509005531");
		resp.put("sign", WXPayUtil.generateSignature(resp, WXPayConfigImpl
				.getInstance().getKey(), SignType.MD5));
		return resp;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(WXPayUtil.generateSignature(test(null),
				WXPayConfigImpl.getInstance().getKey(), SignType.MD5));
	}

}
