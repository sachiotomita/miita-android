package com.naoto.yamaguchi.miita.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application class.
 *
 * Created by naoto on 16/06/23.
 */
public class MiitaApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Context
    MiitaContext.onCreateApplication(getApplicationContext());

    // Realm
    RealmConfiguration config = new RealmConfiguration.Builder(this).build();
    Realm.setDefaultConfiguration(config);
  }
}
