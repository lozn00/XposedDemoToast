package com.example.xposeddemo;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by lozn on 2021/9/30 qssq521@gmail.com
 */

public class Main implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    private static final String TAG = "Main";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        Log.w(TAG,"handleLoadPackage;"+loadPackageParam.packageName);
        if(loadPackageParam.packageName.startsWith("com.android")){
            XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {

                    Application application = (Application) param.thisObject;
                    application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                        @Override
                        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                            Log.w(TAG,"Act create:"+activity.getClass().getName());
                            Toast.makeText(activity, "create:"+activity.getClass().getSimpleName(), Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onActivityStarted(@NonNull Activity activity) {

                        }

                        @Override
                        public void onActivityResumed(@NonNull Activity activity) {

                        }

                        @Override
                        public void onActivityPaused(@NonNull Activity activity) {

                        }

                        @Override
                        public void onActivityStopped(@NonNull Activity activity) {

                        }

                        @Override
                        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

                        }

                        @Override
                        public void onActivityDestroyed(@NonNull Activity activity) {
                            Log.w(TAG,"Act destory:"+activity.getClass().getName());

                        }
                    });
                }
            });
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        Log.w(TAG,"modulePath;"+startupParam.modulePath);

    }
}
