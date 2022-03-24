package com.elnassera.sdk.screen.service;

import com.elnassera.sdk.screen.configuration.ViplexCore;
import com.elnassera.sdk.screen.model.Request;
import com.elnassera.sdk.screen.model.ScreenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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

	public String invokeMethod(Request request) {
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

		Object[] params = new Object[] { request.getData().toString(), (ViplexCore.CallBack) callBack };
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

	public void login(Request request) throws InterruptedException {

		Map data = (HashMap) request.getData();
		String sn = data.get("sn").toString();
		ScreenProperties props = credentialLoader.loadPropsForSN(sn);
		String loginParam = String.format("{\"sn\":\"%s\",\"username\":\"%s\",\"rememberPwd\":1,\"password\":\"%s\",\"loginType\":0}", sn,
				props.getUsername(), props.getPassword());
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
