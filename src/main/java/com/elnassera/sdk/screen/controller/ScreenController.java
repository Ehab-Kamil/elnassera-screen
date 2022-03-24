package com.elnassera.sdk.screen.controller;

import com.elnassera.sdk.screen.configuration.ViplexCore;
import com.elnassera.sdk.screen.model.Test;
import com.elnassera.sdk.screen.service.Details;
import com.elnassera.sdk.screen.service.ReflectionUtil;
import com.elnassera.sdk.screen.service.ScreenService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

import org.springframework.web.context.annotation.RequestScope;

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

	@Autowired
	Details g;

	@Autowired
	private ViplexCore viplexCore;
	@Autowired
	private ScreenService screenService;

	Boolean g_bAPIReturn = false;
	String callBackData = "";

	@GetMapping(path = "/test")
	public String getTableStructure() throws InterruptedException {

		ViplexCore.CallBack callBack = new ViplexCore.CallBack() {

			@Override
			public void dataCallBack(int code, String data) {
				// TODO Auto-generated method stub
				String strCode = "\nViplexCore Demo code:" + code;
				String strData = "\nViplexCore Demo data:" + data;
				System.out.println(strCode);
				System.out.println(strData);
				g_bAPIReturn = true;
				callBackData = data;
			}
		};

		viplexCore.nvSetScreenBrightnessAsync("{\"sn\":\"MZSA71304W0040003623\", \"screenBrightnessInfo\":{\"ratio\":70.0}", callBack);
		return "Done";
	}

	@GetMapping(path = "/testGetDetails")
	public void details(@RequestHeader("SN") String sn) {
		//		GetDetails g=new GetDetails();
		g.detailsForSN(sn);
		System.out.println("IP is : " + g.getIP());
		System.out.println("name is : " + g.getUserName());
		System.out.println("password is : " + g.getPassword());
	}

	@PostMapping(value = "/request", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String handleRequest(@RequestBody JSONObject request) {

		return screenService.invokeMethod(request);
	}
}
