package com.elnassera.sdk.screen.service;

import com.elnassera.sdk.screen.configuration.ViplexCore;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ReflectionUtil {

    public void invokeMethodByReflection(Object srcObject, String methodName, Object[] params)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse("2022-05-29");
		Date now = new Date();

		if (date1.before(now)) {
			throw new Exception("Trial Version has been expired ");
		}

    	Class[] classes = new Class[] {String.class, ViplexCore.CallBack.class};
//        for (int i = 0; i < params.length; i++) classes[i] = params[i].getClass();
        invokeMethodByReflection(srcObject, srcObject.getClass(), methodName, params, classes);
    }

    private void invokeMethodByReflection(Object srcObject, Class<?> clazz, String methodName, Object[] params, Class[] classes) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = clazz.getDeclaredMethod(methodName, classes);
        method.setAccessible(true);
        method.invoke(srcObject, params);
    }
}
