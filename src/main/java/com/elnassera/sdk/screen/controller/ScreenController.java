package com.elnassera.sdk.screen.controller;

import com.elnassera.sdk.screen.configuration.ViplexCore;
import com.elnassera.sdk.screen.model.Request;
import com.elnassera.sdk.screen.model.Test;
import com.elnassera.sdk.screen.service.ProptertiesLoader;
import com.elnassera.sdk.screen.service.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.context.annotation.RequestScope;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	ProptertiesLoader g;

	@Autowired
	private ViplexCore viplexCore;
	@Autowired
	private ScreenService screenService;

	@Value("${searchTimeout}")
	private String searchTimeout;

	Boolean g_bAPIReturn = false;
	String callBackData = "";

	@GetMapping(path = "/test")
	public String testMethod() throws InterruptedException {
		Test.testApi();
		//		ViplexCore.CallBack callBack = new ViplexCore.CallBack() {
		//
		//			@Override
		//			public void dataCallBack(int code, String data) {
		//				// TODO Auto-generated method stub
		//				String strCode = "\nViplexCore Demo code:" + code;
		//				String strData = "\nViplexCore Demo data:" + data;
		//				System.out.println(strCode);
		//				System.out.println(strData);
		//				g_bAPIReturn = true;
		//				callBackData = data;
		//			}
		//		};
		//
		//		viplexCore.nvSetScreenBrightnessAsync("{\"sn\":\"MZSA71304W0040003623\", \"screenBrightnessInfo\":{\"ratio\":70.0}",
		//		callBack);
		return "Done";
	}

	@GetMapping(path = "/searchIp")
	public List search() throws InterruptedException {
		return screenService.searchIp(searchTimeout);
	}

	@PostMapping(value = "/request", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String handleRequest(@RequestBody Request request) throws InterruptedException, ParseException, Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse("2022-05-29");
		Date now = new Date();

		if (date1.before(now)) {
			return "Trial Version has been expired ";
		}

		if (request.getData().getAsString("sn") != null)
			screenService.login(request);
		return screenService.invokeMethod(request);
	}
}
