package com.jianq.manager.spm.payment.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.jianq.manager.spm.order.service.IOrderService;
import com.jianq.manager.util.ReturnUtil;
import com.jianq.manager.util.StaticPropertiesUtil;
import com.jianq.manager.util.StringUtil;

@RestController
@RequestMapping("/**/alipay")
public class AlipayController {

	private static final Logger log = LoggerFactory
			.getLogger(AlipayController.class);

	// 应用基本信息
	private static String APP_ID = StaticPropertiesUtil
			.getValue("alipay.app.id");
	private static String APP_PRIVATE_KEY = StaticPropertiesUtil
			.getValue("alipay.private.key");
	private static String ALIPAY_PUBLIC_KEY = StaticPropertiesUtil
			.getValue("alipay.public.key");

	@Autowired
	private IOrderService orderService;

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
	public static Object generateOrderInfo(String orderId, String amount) {
		if (StringUtil.isBlank(orderId) || StringUtil.isBlank(amount)) {
			return ReturnUtil.fail("订单号和金额不能为空");
		}

		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(
				"https://openapi.alipay.com/gateway.do", APP_ID,
				APP_PRIVATE_KEY, "json", "utf-8", ALIPAY_PUBLIC_KEY, "RSA2");
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("红星美凯龙-商品");
		model.setSubject("红星美凯龙-商品");
		model.setOutTradeNo(orderId);
		model.setTimeoutExpress("30m");
		model.setTotalAmount(amount);
		model.setProductCode("QUICK_MSECURITY_PAY");// 写死
		request.setBizModel(model);
		request.setNotifyUrl(StaticPropertiesUtil
				.getValue("current.server.url.domain")
				+ "/hongxing/app/alipay/check");
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient
					.sdkExecute(request);
			System.out.println(response.getBody());// 就是orderString
			// 可以直接给客户端请求，无需再做处理。
			return ReturnUtil.success("生成预订单成功", response.getBody());
		} catch (AlipayApiException e) {
			log.error("生成预订单 提交到支付宝接口 出现异常：{}", e);
		}
		return ReturnUtil.fail("生成预订单失败");
	}

	/**
	 * 支付宝回调接口：验证异步通知信息
	 */
	@RequestMapping("check")
	public Object check(HttpServletRequest request) {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			log.info("回调接口中返回参数：valueStr={}", valueStr);
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		// 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		// boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String
		// publicKey, String charset, String sign_type)
		try {
			boolean flag = AlipaySignature.rsaCheckV1(params,
					ALIPAY_PUBLIC_KEY, "utf-8", "RSA2");
			System.out.println("flag====" + flag);
			if (flag) {
				int orderId = 0;
				try {
					orderId = Integer.parseInt(params.get("out_trade_no"));
					orderService.paymentCompleted(orderId);
				} catch (Exception e) {
					log.error("支付宝回调接口：订单号解析异常.orderId={},e={}", orderId, e);
				}
			}
			return flag;
		} catch (AlipayApiException e) {
			log.error("支付宝异步回调接口 出现异常：{}", e);
		}
		return false;
	}
}
