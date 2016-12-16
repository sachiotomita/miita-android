package com.naoto.yamaguchi.miita.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Progress Dialog Builder.
 *
 * Created by naoto on 2016/12/13.
 */

public final class MiitaProgressDialogBuilder {
    private final Context context;
    private final MiitaProgressDialogType type;
    private final ProgressDialog dialog;

    public MiitaProgressDialogBuilder(Context context, MiitaProgressDialogType type) {
        this.context = context;
        this.type = type;
        this.dialog = new ProgressDialog(this.context);
    }
    
    public MiitaProgressDialogBuilder build() {
        this._build();
        return this;
    }

    public void show() {
        this.dialog.show();
    }

    private void _build() {
        this.dialog.setTitle(this.type.getTitle());
        this.dialog.setMessage(this.type.getMessage());
        this.dialog.setCancelable(false);
    }
}
