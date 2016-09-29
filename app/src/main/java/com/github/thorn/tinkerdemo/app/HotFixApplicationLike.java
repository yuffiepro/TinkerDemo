package com.github.thorn.tinkerdemo.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.github.thorn.tinkerdemo.Log.MyLogImp;
import com.github.thorn.tinkerdemo.util.HotFixApplicationContext;
import com.github.thorn.tinkerdemo.util.TinkerManager;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Desc:
 * Created by Thorn on 16/9/29.
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(
        application = "com.github.thorn.tinkerdemo.app.HotFixApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false
)
public class HotFixApplicationLike extends DefaultApplicationLike {

    public HotFixApplicationLike(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent, Resources[] resources, ClassLoader[] classLoader, AssetManager[] assetManager) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent, resources, classLoader, assetManager);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        HotFixApplicationContext.application = getApplication();
        HotFixApplicationContext.context = getApplication();
        TinkerManager.setTinkerApplicationLike(this);
        TinkerManager.initFastCrashProtect();

        TinkerManager.setUpgradeRetryEnable(true);
        TinkerInstaller.setLogIml(new MyLogImp());

        TinkerManager.installTinker(this);

    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }
}
