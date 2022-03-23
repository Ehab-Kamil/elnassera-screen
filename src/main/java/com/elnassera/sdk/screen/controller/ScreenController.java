package com.elnassera.sdk.screen.controller;

import com.elnassera.sdk.screen.configuration.ViplexCore;
import com.elnassera.sdk.screen.model.Request;
import com.elnassera.sdk.screen.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Title: ScreenController.java
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright(c) Ehab Kamil, 2022
 * </p>
 *
 * @author <a href="mailto:ehabkamil2@gmail.com">Ehab Attia</a>
 * @version 1.0
 * @date 25/02/2022
 */
@RestController
@RequestMapping(value = "/screen")
@CrossOrigin
@RequestScope
public class ScreenController {

	Boolean g_bAPIReturn = false;
	int g_code = 0;
	String strData = "";

	@Autowired
	private ViplexCore instance;

	@PostMapping(path = "/request")
	public String getTableStructure(@RequestBody Request request) throws InterruptedException {

//				Test.testApi();
		String g_sn = "MZSA71304W0040003623"; //BZSA07313J0350000997

		ViplexCore.CallBack callBack = new ViplexCore.CallBack() {

			@Override
			public void dataCallBack(int code, String data) {
				String strCode = "\nViplexCore Demo code:" + code;
				System.out.println(strCode);
				System.out.println("\nViplexCore Demo data:" + data);
				g_bAPIReturn = true;
			}

		};

		ViplexCore.CallBack actualResult = new ViplexCore.CallBack() {

			@Override
			public void dataCallBack(int code, String data) {
				// TODO Auto-generated method stub
				g_code = code;
				String strCode = "\nViplexCore Demo code:" + code;
				strData = " " + data;
				System.out.println(strCode);
				System.out.println(strData);
				g_bAPIReturn = true;
			}

		};

		String loginParam = String
				.format("{\"sn\":\"%s\",\"username\":\"admin\",\"rememberPwd\":1,\"password\":\"123456\",\"loginType\":0}", g_sn);
		instance.nvLoginAsync(loginParam, callBack);
		waitAPIReturn();

//		String setVolumeParam = String.format("{\"sn\":\"%s\",\"screenBrightnessInfo\":{\"ratio\":80.0}}", g_sn);
		instance.nvSetScreenBrightnessAsync(request.getData().toString(), actualResult);

		while(!g_bAPIReturn) {
			Thread.sleep(1000);
		}
		g_bAPIReturn = false;
		g_code = 0;

		return new StringBuilder(strData).toString();
	}

	void waitAPIReturn() throws InterruptedException {
		while(!g_bAPIReturn) {
			Thread.sleep(1000);
		}
		g_bAPIReturn = false;
	}
}
