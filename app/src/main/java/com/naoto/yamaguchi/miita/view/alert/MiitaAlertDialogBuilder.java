package com.naoto.yamaguchi.miita.view.alert;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

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
        this.builder.setTitle(this.type.getTitleRes())
                .setMessage(this.type.getMessageRes())
                .setCancelable(false);

        if (this.type.isHasPositiveButton()) {
            this.builder.setPositiveButton(this.type.getPositiveButtonRes(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            callPositiveButtonClick();
                        }
                    });
        }

        if (this.type.isHasNegativeButton()) {
            this.builder.setNegativeButton(this.type.getNegativeButtonRes(),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            callNegativeButtonClick();
                        }
                    });
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
