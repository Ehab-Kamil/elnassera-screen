package com.elnassera.sdk.screen.service;

import com.elnassera.sdk.screen.configuration.ViplexCore;
import net.minidev.json.JSONObject;
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

    Boolean g_bAPIReturn;
    String callBackData;

    public String invokeMethod(JSONObject request) {
        g_bAPIReturn = false;
        callBackData = "";
        String userName = request.get("username").toString();
        String password = request.get("password").toString();
        String methodName = request.get("function").toString();
        Map data = (HashMap) request.get("data");
        String sn = data.get("sn").toString();

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

        Object[] params = new Object[]{request.get("data").toString(), (ViplexCore.CallBack) callBack};
        try {
            reflectionUtil.invokeMethodByReflection(viplexCore, request.get("function").toString(), params);
            waitAPIReturn();

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            g_bAPIReturn = true;
            callBackData = String.format("Error While Calling Function (%s) due to: %s", methodName, e.getMessage());

        } catch (InterruptedException e) {
            e.printStackTrace();
            g_bAPIReturn = true;
            callBackData = String.format("Error While Calling Function (%s) due to: %s", methodName, e.getMessage());
        }

        return callBackData;
    }

    private void waitAPIReturn() throws InterruptedException {
        while(!g_bAPIReturn) {
            Thread.sleep(1000);
        }
        g_bAPIReturn = false;
    }
}
