package com.elnassera.sdk.screen.service;

import com.elnassera.sdk.screen.configuration.ViplexCore;
import com.elnassera.sdk.screen.model.Request;
import com.elnassera.sdk.screen.model.ScreenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//business logic
@Service
public class ScreenService {

	@Autowired
	private ReflectionUtil reflectionUtil;
	@Autowired
	private ViplexCore viplexCore;

	@Autowired
	private ProptertiesLoader credentialLoader;

	Boolean g_bAPIReturn = false;
	String callBackData = "	";

	public List searchIp(String searchTimeout) throws InterruptedException {
		ArrayList bulkCallBackData = new ArrayList<String>();
		g_bAPIReturn = false;
		callBackData = "";

		ViplexCore.CallBack callBack = new ViplexCore.CallBack() {

			@Override
			public void dataCallBack(int code, String data) {
				// TODO Auto-generated method stub
				String strCode = "\nViplexCore Demo code:" + code;
				String strData = "\nViplexCore Demo data:" + data;
				System.out.println(strCode);
				System.out.println(strData);
				g_bAPIReturn = true;
				bulkCallBackData.add(data);
			}
		};

		viplexCore.nvSearchTerminalAsync(callBack);

		Thread.sleep(Long.parseLong(searchTimeout));
		return bulkCallBackData;
	}

	public String invokeMethod(Request request) throws Exception {
		g_bAPIReturn = false;
		callBackData = "";

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

		Object[] params;

		if (request.getData() != null) {
			params = new Object[] { request.getData().toString(), (ViplexCore.CallBack) callBack };
		} else {
			params = new Object[] { (ViplexCore.CallBack) callBack };
		}

		try {
			reflectionUtil.invokeMethodByReflection(viplexCore, request.getFunction(), params);
			waitAPIReturn();

		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			g_bAPIReturn = true;
			callBackData = String.format("Error While Calling Function (%s) due to: %s", request.getFunction(), e.getMessage());

		} catch (InterruptedException e) {
			e.printStackTrace();
			g_bAPIReturn = true;
			callBackData = String.format("Error While Calling Function (%s) due to: %s", request.getFunction(), e.getMessage());
		}

		return callBackData;
	}

	public void login(Request request) throws InterruptedException, Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse("2022-05-29");
		Date now = new Date();

		if (date1.before(now)) {
			throw new Exception("Trial Version has been expired ");
		}

		Map data = (HashMap) request.getData();
		String sn = data.get("sn").toString();
		ScreenProperties props = credentialLoader.loadPropsForSN(sn);
		String loginParam = String
				.format("{\"sn\":\"%s\",\"ip\":\"%s\",\"username\":\"%s\",\"rememberPwd\":1,\"password\":\"%s\"," + "\"loginType\":0}", sn,
						props.getIp(), props.getUsername(), props.getPassword());
		viplexCore.nvLoginAsync(loginParam, new ViplexCore.CallBack() {

			@Override
			public void dataCallBack(int code, String data) {
				// TODO Auto-generated method stub
				String strCode = "\nViplexCore Demo code:" + code;
				String strData = "\nViplexCore Demo data:" + data;
				System.out.println(strCode);
				System.out.println(strData);
				g_bAPIReturn = true;
			}
		});
		waitAPIReturn();

	}

	private void waitAPIReturn() throws InterruptedException {
		while(!g_bAPIReturn) {
			Thread.sleep(1000);
		}
		g_bAPIReturn = false;
	}
}
