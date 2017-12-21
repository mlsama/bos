package com.itheima.bos.utils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 阿里大于的短信发送工具类
 * @author lenovo
 *
 */
public class SmsUtils {

	/**
	 * 发送短信
	 * @return
	 */
	public static String sendSms(String signName,String smsTemp,String recPhone,String smsTempId) {
		// 1.创建发送的工具类
		/**
		 * 参数一：平台的url，固定的 参数二：appKey，应用的key 参数三：appScret，应用的密钥
		 */
		String url = "http://gw.api.taobao.com/router/rest";
		String appkey = "23670810";
		String secret = "c54b7b38d9dc852951e3991ed5052698";

		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);

		// 2.发送短信的请求对象
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();

		// 扩展参数（可选）
		req.setExtend("123456");
		// 发送类型
		req.setSmsType("normal");
		// 短信签名 （*）
		req.setSmsFreeSignName(signName);
		// 设置短信模块的参数（*）
		req.setSmsParamString(smsTemp);
		// 接收的手机号码（*）
		req.setRecNum(recPhone);
		// 短信模块ID（*）
		req.setSmsTemplateCode(smsTempId);

		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}

		return rsp.getBody();
	}
}
