package com.example.myplugintest.utils;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class HookUtil {
    public static void hookAms() {
        try {
            Object singleTon = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                /**
                 * android 29 或以上版本的api
                 */
                Class<?> activityManagerClass = Class.forName("android.app.ActivityTaskManager");
                Field iActivityManagerSingletonField = activityManagerClass.getDeclaredField("IActivityTaskManagerSingleton");
                iActivityManagerSingletonField.setAccessible(true);
                singleTon = iActivityManagerSingletonField.get(null);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                /**
                 * android 26 或以上版本的api
                 */
                Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");
                Field iActivityManagerSingleTonField = activityManagerClass.getDeclaredField("IActivityManagerSingleton");
                iActivityManagerSingleTonField.setAccessible(true);
                singleTon = iActivityManagerSingleTonField.get(null);
            } else {
                /**
                 * android 26以下版本api
                 */
                Class<?> activityManagerClass = Class.forName("android.app.ActivityManagerNative");
                Field iActivityManagerSingleTonField = activityManagerClass.getDeclaredField("gDefault");
                iActivityManagerSingleTonField.setAccessible(true);
                singleTon = iActivityManagerSingleTonField.get(null);
            }

            Class<?> singleTonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singleTonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            // 获取到IActivityManagerSingleton的对象
            final Object mInstance = mInstanceField.get(singleTon);

            Class<?> iActivityManagerClass;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                iActivityManagerClass = Class.forName("android.app.IActivityTaskManager");
            } else {
                iActivityManagerClass = Class.forName("android.app.IActivityManager");
            }

            Object newInstance = Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{iActivityManagerClass},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (method.getName().equals("startActivity")) {
                                int index = 0;
                                for (int i = 0; i < args.length; i++) {
                                    if (args[i] instanceof Intent) {
                                        index = i;
                                        break;
                                    }
                                }

                                Intent proxyIntent = new Intent();
                                proxyIntent.setClassName("com.example.myplugintest",
                                        "com.example.myplugintest.ProxyActivity");
                                proxyIntent.putExtra("oldIntent", (Intent) args[index]);
                                args[index] = proxyIntent;
                            }

                            return method.invoke(mInstance, args);
                        }
                    }
            );

            mInstanceField.set(singleTon, newInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hookHandler() {
        try {
            // 获取ActivityThread实例
            final Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field activityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            activityThreadField.setAccessible(true);
            final Object activityThread = activityThreadField.get(null);

            // 获取Handler实例
            Field mHField = activityThreadClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            Object mH = mHField.get(activityThread);

            Class<?> handlerClass = Class.forName("android.os.Handler");
            Field mCallbackField = handlerClass.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);
            mCallbackField.set(mH, new Handler.Callback() {

                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    System.out.println("yp====  handling code = " + msg.what);

                    switch (msg.what) {
                        case 100: // API 28 以前直接接收
                            try {
                                // 获取ActivityClientRecord中的intent对象
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                Intent proxyIntent = (Intent) intentField.get(msg.obj);

                                // 拿到插件的intent
                                Intent intent = proxyIntent.getParcelableExtra("oldIntent");

                                // 替换回来
                                proxyIntent.setComponent(intent.getComponent());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case 159: // API 28 以后加入了lifecycle， 这里msg发生了变化
                            try {
                              Field mActivityCallbacksField = msg.obj.getClass().getDeclaredField("mActivityCallbacks");
                              mActivityCallbacksField.setAccessible(true);
                              List<Object> mActivityCallbacks = (List<Object>) mActivityCallbacksField.get(msg.obj);
                              for (int i = 0; i < mActivityCallbacks.size(); i++) {
                                  Class<?> itemClass = mActivityCallbacks.get(i).getClass();
                                  if (itemClass.getName().equals("android.app.servertransaction.LaunchActivityItem")) {
                                      Field intentField = itemClass.getDeclaredField("mIntent");
                                      intentField.setAccessible(true);
                                      Intent proxyIntent = (Intent) intentField.get(mActivityCallbacks.get(i));
                                      Intent intent = proxyIntent.getParcelableExtra("oldIntent");
                                      proxyIntent.setComponent(intent.getComponent());
                                  }
                              }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    // 这里必须返回false
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
