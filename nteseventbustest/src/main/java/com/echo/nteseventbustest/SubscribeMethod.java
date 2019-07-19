package com.echo.nteseventbustest;

import java.lang.reflect.Method;

public class SubscribeMethod {
    private Method mMethod;
    private ThreadMode mThreadMode;



    private Class<?> mType;

    public SubscribeMethod(Method method, ThreadMode threadMode, Class<?> aClass) {
        mMethod = method;
        mThreadMode = threadMode;
        mType = aClass;
    }



    public Method getMethod() {
        return mMethod;
    }

    public void setMethod(Method method) {
        mMethod = method;
    }

    public ThreadMode getThreadMode() {
        return mThreadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        mThreadMode = threadMode;
    }


    public Class<?> getType() {
        return mType;
    }

    public void setType(Class<?> type) {
        mType = type;
    }
}
