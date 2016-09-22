package com.naoto.yamaguchi.miita.model.base;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

/**
 * Created by naoto on 16/09/22.
 */
public abstract class BaseNoObjectModel<T> extends BaseModel<T> {
    public BaseNoObjectModel(Context context) {
        super(context);
    }

    protected void deliverSuccess() {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                listener.onSuccess(null);
                listener.onComplete();
            }
        });
    }

    protected void deliverError(final APIException e) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                listener.onError(e);
                listener.onComplete();
            }
        });
    }
}
