package com.naoto.yamaguchi.miita.util.device;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.naoto.yamaguchi.miita.application.MiitaContext;

/**
 * Device info.
 *
 * Created by naoto on 2017/01/17.
 */

public final class Device {

    public static int getApiLevel() {
        return Build.VERSION.SDK_INT;
    }

    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    // TODO: move identifier class.
    public static int getAppVersionCode() {
        final Context context = MiitaContext.getInstance().getContext();
        final PackageManager pm = context.getPackageManager();
        int code = 0;

        try {
            final PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return code;
        }

        return code;
    }

    public static String getAppVersionName() {
        final Context context = MiitaContext.getInstance().getContext();
        final PackageManager pm = context.getPackageManager();
        String name = "";

        try {
            final PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return name;
        }

        return name;
    }

    private Device() {}
}
