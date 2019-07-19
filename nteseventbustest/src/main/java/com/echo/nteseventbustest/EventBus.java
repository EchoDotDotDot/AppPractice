package com.echo.nteseventbustest;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EventBus {
    static EventBus sEventBus = new EventBus();
    HashMap<Object, List<SubscribeMethod>> cachemap = new HashMap<>();
    Handler mHandler = new Handler();
    ThreadPoolExecutor mThreadPoolExecutor = new ThreadPoolExecutor(2, 8, 1, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(100));
    private EventBus() {
    }

    public static EventBus getInstance() {
        return sEventBus;
    }

    public void register(Object object) {
        List<SubscribeMethod> lm = cachemap.get(object);
        if (lm == null) {
            lm = findregistermethod(object);
            cachemap.put(object, lm);
        }
    }

    public List<SubscribeMethod> findregistermethod(Object o) {
        List<SubscribeMethod> methodList = new ArrayList<>();
        Class<?> clazz = o.getClass();
        while (clazz != null) {
            if (clazz.getName().startsWith("android.") || clazz.getName().startsWith("android.") || clazz.getName().startsWith("android.")) {
                break;
            }

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                Annotation annotation = method.getAnnotation(Subscribe.class);
                if (annotation == null) {
                    continue;
                }
                Class<?>[] types = method.getParameterTypes();
                if (types.length > 1) {

                    Log.e("=======", "eventbus cannot accept more than 1 param");
                    continue;
                }

                SubscribeMethod subscribeMethod = new SubscribeMethod(method,
                        ((Subscribe) annotation).threadmode(), types[0]);
                methodList.add(subscribeMethod);
            }
            clazz = clazz.getSuperclass();
        }
        return methodList;
    }

    public void post(final Object type) {
        Set<Object> set = cachemap.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            final Object object = iterator.next();
            List<SubscribeMethod> subscribeMethods = cachemap.get(object);
            for (final SubscribeMethod subscribeMethod : subscribeMethods) {
                if (subscribeMethod.getType().isAssignableFrom(type.getClass())) {
                    ThreadMode threadMode = subscribeMethod.getThreadMode();
                    switch (threadMode) {
                        case MAIN:
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                invoke(subscribeMethod, object, type);
                            } else {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribeMethod, object, type);
                                    }
                                });
                            }
                            break;
                        case BACKGROUD:
                            mThreadPoolExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    invoke(subscribeMethod, object, type);
                                }
                            });
                            break;
                    }

                }
            }
        }
    }

    public void invoke(SubscribeMethod subscribeMethod, Object object, Object type) {
        Method method = subscribeMethod.getMethod();
        try {
            method.invoke(object, type);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
