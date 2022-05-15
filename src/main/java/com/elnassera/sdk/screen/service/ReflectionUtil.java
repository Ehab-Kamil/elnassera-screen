package com.elnassera.sdk.screen.service;

import com.elnassera.sdk.screen.configuration.ViplexCore;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class ReflectionUtil {

    public void invokeMethodByReflection(Object srcObject, String methodName, Object[] params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class[] classes = new Class[] { ViplexCore.CallBack.class};
//        for (int i = 0; i < params.length; i++) classes[i] = params[i].getClass();
        invokeMethodByReflection(srcObject, srcObject.getClass(), methodName, params, classes);
    }

    private void invokeMethodByReflection(Object srcObject, Class<?> clazz, String methodName, Object[] params, Class[] classes) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = clazz.getDeclaredMethod(methodName, classes);
        method.setAccessible(true);
        method.invoke(srcObject, params);
    }
}
