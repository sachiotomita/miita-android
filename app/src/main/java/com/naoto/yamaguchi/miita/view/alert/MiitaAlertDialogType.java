package com.naoto.yamaguchi.miita.view.alert;

import android.support.annotation.StringRes;

import com.naoto.yamaguchi.miita.R;

/**
 * Alert Type.
 *
 * Created by naoto on 16/08/21.
 */
public enum MiitaAlertDialogType {
    LOGIN(R.string.alert_login_title, R.string.alert_login_message, true,
            R.string.alert_login_ok, true, R.string.alert_login_cancel);

    private final @StringRes int titleRes;
    private final @StringRes int messageRes;
    private final boolean hasPositiveButton;
    private final @StringRes int positiveButtonRes;
    private final boolean hasNegativeButton;
    private final @StringRes int negativeButtonRes;

    MiitaAlertDialogType(@StringRes int titleRes, @StringRes int messageRes,
                         boolean hasPositiveButton, @StringRes int positiveButtonRes,
                         boolean hasNegativeButton, @StringRes int negativeButtonRes) {
        this.titleRes = titleRes;
        this.messageRes = messageRes;
        this.hasPositiveButton = hasPositiveButton;
        this.positiveButtonRes = positiveButtonRes;
        this.hasNegativeButton = hasNegativeButton;
        this.negativeButtonRes = negativeButtonRes;
    }

    public @StringRes int getTitleRes() {
        return this.titleRes;
    }

    public @StringRes int getMessageRes() {
        return this.messageRes;
    }

    public boolean isHasPositiveButton() {
        return this.hasPositiveButton;
    }

    public @StringRes int getPositiveButtonRes() {
        return this.positiveButtonRes;
    }

    public boolean isHasNegativeButton() {
        return this.hasNegativeButton;
    }

    public @StringRes int getNegativeButtonRes() {
        return this.negativeButtonRes;
    }
}
