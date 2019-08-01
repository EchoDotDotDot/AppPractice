package com.echo.aroute;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

public class ARouter {
    private static ARouter sARouter=new ARouter();
    private ARouter(){
        activitylist=new HashMap<>();
    }
    public static ARouter getInstance(){
        return sARouter;
    }

    private Context mContext;

    private Map<String,Class<? extends Activity>> activitylist;

    public void putActivity(String path,Class<? extends Activity> clazz){
        activitylist.put(path,clazz);
    }

    public void init(Application application){
        this.mContext=application;

        List<String> classNames = getClassName("com.netease.util");
        for (String className : classNames) {
            Class<?> aClass = null;
            try {
                aClass = Class.forName(className);
                if(IRouter.class.isAssignableFrom(aClass)){
                    IRouter o = (IRouter)aClass.newInstance();
                    o.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void jumpActivity(String path, Bundle bundle){
        Class<?extends Activity> clazz=activitylist.get(path);
        if(clazz==null){
            return;
        }
        Intent intent=new Intent().setClass(mContext,clazz).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(bundle!=null){
            intent.putExtra("bundle",bundle);
        }

        mContext.startActivity(intent);
    }

    private List<String> getClassName(String packageName){
        List<String> classList=new ArrayList<>();
        String path=null;
        try {
            path=mContext.getPackageManager().getApplicationInfo(mContext.getPackageName(),0).sourceDir;
            DexFile dexFile=new DexFile(path);
            Enumeration enumeration=dexFile.entries();
            while (enumeration.hasMoreElements()){
                String name=(String)enumeration.nextElement();
                if(name.contains(packageName)){
                    classList.add(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classList;
    }
}
