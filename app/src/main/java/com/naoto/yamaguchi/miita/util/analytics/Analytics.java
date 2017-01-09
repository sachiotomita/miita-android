package com.naoto.yamaguchi.miita.util.analytics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.naoto.yamaguchi.miita.BuildConfig;

/**
 * FirebaseAnalytics Helper.
 * Reference: {@link FirebaseAnalytics}
 *
 * Created by naoto on 2017/01/09.
 */

public final class Analytics {
    public enum ActionType {
        STOCK("stock"),
        UNSTOCK("unstock");

        private final String type;

        ActionType(@NonNull String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }
    }

    private static Analytics instance = null;
    private FirebaseAnalytics firebaseAnalytics;

    public static synchronized Analytics getInstance() {
        if (instance == null) {
            instance = new Analytics();
        }
        return instance;
    }

    private Analytics() {}

    public void onCreate(Context context) {
        if (this.isDebug()) {
            // TODO: error message
        } else {
            this.firebaseAnalytics = FirebaseAnalytics.getInstance(context);
        }
    }

    public void sendEvent(ActionType contentType, String itemId) {
        if (this.isDebug()) {
            // TODO: error message
            return;
        }

        if (this.firebaseAnalytics == null) {
            // TODO: error message
            return;
        }

        final Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType.getType());
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, itemId);
        this.firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    private boolean isDebug() {
        return BuildConfig.DEBUG;
    }
}
