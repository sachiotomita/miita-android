package com.naoto.yamaguchi.miita.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * {@ProgressDialog} child class.
 *
 * Created by naoto on 2016/12/18.
 */

public final class MiitaProgressDialog extends ProgressDialog {
    private final Context context;
    private final MiitaProgressDialogType type;

    public MiitaProgressDialog(Context context, MiitaProgressDialogType type) {
        super(context);
        this.context = context;
        this.type = type;
    }

    public MiitaProgressDialog build() {
        this._build();
        return this;
    }

    private void _build() {
        this.setTitle(this.type.getTitle());
        this.setMessage(this.type.getMessage());
        this.setCancelable(false);
    }
}
