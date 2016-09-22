package com.naoto.yamaguchi.miita.model.base;

import android.content.Context;

/**
 * Created by naoto on 16/09/22.
 */
public abstract class BaseModel<T> {

    protected Context context;
    protected OnModelListener<T> listener;

    protected BaseModel(Context context) {
        this.context = context;
    }

    protected void addModelListener(OnModelListener listener) {
        this.listener = listener;
    }
}
