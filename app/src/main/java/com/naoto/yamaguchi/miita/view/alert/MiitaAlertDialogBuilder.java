package com.naoto.yamaguchi.miita.view.alert;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.application.Constants;

/**
 * Provide Alert Dialog each {@MiitaAlertDialogType}.
 *
 * Created by naoto on 16/08/21.
 */
public final class MiitaAlertDialogBuilder {
    private final Context context;
    private final MiitaAlertDialogType type;
    private final AlertDialog.Builder builder;
    private MiitaAlertDialogListener positiveListener = null;
    private MiitaAlertDialogListener negativeListener = null;

    public MiitaAlertDialogBuilder(Context context, MiitaAlertDialogType type) {
        this.context = context;
        this.type = type;
        this.builder = new AlertDialog.Builder(context);
    }

    public MiitaAlertDialogBuilder setPositiveListener(
            MiitaAlertDialogListener listener) {
        this.positiveListener = listener;
        return this;
    }

    public MiitaAlertDialogBuilder setNegativeListener(
            MiitaAlertDialogListener listener) {
        this.negativeListener = listener;
        return this;
    }

    public MiitaAlertDialogBuilder build() {
        this._build();
        return this;
    }

    public void show() {
        this.builder.show();
    }

    private void _build() {
        switch (this.type) {
            case LOGIN:
                this.builder.setTitle(R.string.alert_login_title)
                        .setMessage(R.string.alert_login_message)
                        .setPositiveButton(R.string.alert_login_ok,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface,
                                                        int i) {
                                        Uri uri = Uri.parse(Constants.AUTHORIZE_URL);
                                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                                uri);
                                        context.startActivity(intent);
                                        callPositiveButtonClick();
                                    }
                                }
                        )
                        .setNegativeButton(R.string.alert_login_cancel,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface,
                                                        int i) {
                                        callNegativeButtonClick();
                                    }
                                }
                        );
                break;
        }
    }

    private void callPositiveButtonClick() {
        if (this.positiveListener != null) {
            this.positiveListener.onClick();
        }
    }

    private void callNegativeButtonClick() {
        if (this.negativeListener != null) {
            this.negativeListener.onClick();
        }
    }
}
