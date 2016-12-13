package com.naoto.yamaguchi.miita.view.dialog;

import android.support.annotation.NonNull;

/**
 * Progress Dialog Type.
 *
 * Created by naoto on 2016/12/13.
 */

public enum MiitaProgressDialogType {
    LOGGING_IN("ログイン", "ログイン中...");

    private final @NonNull String title;
    private final @NonNull String message;

    MiitaProgressDialogType(@NonNull String title, @NonNull String message) {
        this.title = title;
        this.message = message;
    }

    public @NonNull String getTitle() {
        return this.title;
    }

    public @NonNull String getMessage() {
        return this.message;
    }
}
