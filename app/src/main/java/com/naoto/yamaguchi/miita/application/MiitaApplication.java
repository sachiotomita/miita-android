package com.naoto.yamaguchi.miita.application;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.naoto.yamaguchi.miita.BuildConfig;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application class.
 * <p>
 * Created by naoto on 16/06/23.
 */
public class MiitaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Context
        MiitaContext.onCreateApplication(getApplicationContext());

        // Fabric
        final Crashlytics crashlytics = new Crashlytics.Builder()
                .core(new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build())
                .build();
        Fabric.with(this, crashlytics);

        // Realm
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
    }
}
