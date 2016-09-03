package com.actionsoft.apps.poc.api.local.api;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlRootElement;

import com.actionsoft.bpms.api.common.ApiResponse;
import com.actionsoft.bpms.server.bind.annotation.Controller;
import com.actionsoft.bpms.server.bind.annotation.HandlerType;
import com.actionsoft.bpms.server.bind.annotation.Mapping;
import com.actionsoft.bpms.server.bind.annotation.Param;
import com.actionsoft.sdk.service.response.StringResponse;

@Controller(type = HandlerType.OPENAPI, apiName = "Demo API", desc = "API扩展开发演示")
@WebService(serviceName = "DemoApi")
public class SayHello {

	// 返回简单String
	@Mapping(value = "demo.say")
	public StringResponse say(@Param(value = "str1", desc = "字符串1", required = true) String str1) {
		StringResponse r = new StringResponse();
		r.setData(str1);
		System.out.println(str1);
		return r;
	}

	// 返回自定义的对象
	@Mapping(value = "demo.calc")
	public CalcResponse calc(@Param(value = "num1", desc = "数字1", required = true) Integer num1, @Param(value = "num2", desc = "数字2", required = true) Integer num2) {
		CalcResponse r = new CalcResponse(num1, num2);
		return r;
	}

}

@XmlRootElement
class CalcResponse extends ApiResponse {
	private int num1;
	private int num2;
	private int num3;

	public CalcResponse() {

	}

	public CalcResponse(int num1, int num2) {
		super();
		this.num1 = num1;
		this.num2 = num2;
		// 计算相加
		num3 = num1 + num2;
	}

	public int getNum1() {
		return num1;
	}

	public void setNum1(int num1) {
		this.num1 = num1;
	}

	public int getNum2() {
		return num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}

	public int getNum3() {
		return num3;
	}

	public void setNum3(int num3) {
		this.num3 = num3;
	}

}
