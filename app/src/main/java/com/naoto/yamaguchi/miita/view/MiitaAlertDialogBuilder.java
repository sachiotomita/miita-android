package com.naoto.yamaguchi.miita.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.application.Constants;

/**
 * Created by naoto on 16/08/21.
 */
public final class MiitaAlertDialogBuilder {

    private Context context;
    private AlertDialog.Builder alert;

    private MiitaAlertDialogBuilder(Context context) {
        this.context = context;
    }

    public MiitaAlertDialogBuilder build(Context context, MiitaAlertDialogType type) {
        this.alert = new AlertDialog.Builder(context);
        this.setTypeParams(type);
        return this;
    }

    public void show() {
        this.alert.show();
    }

    // TODO: more setter Alert Params

    private void setTypeParams(MiitaAlertDialogType type) {
        switch (type) {
            case LOGIN:
                this.alert.setTitle(R.string.alert_login_title)
                        .setMessage(R.string.alert_login_message)
                        .setPositiveButton(R.string.alert_login_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Uri uri = Uri.parse(Constants.AUTHORIZE_URL);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.alert_login_cancel, null);
                break;
        }
    }
}
