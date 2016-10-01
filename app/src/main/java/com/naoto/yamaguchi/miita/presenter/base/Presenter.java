package com.naoto.yamaguchi.miita.presenter.base;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Presenter LifeCycle.
 *
 * Created by naoto on 2016/10/01.
 */

public abstract class Presenter<V> {
    protected final Context context;
    protected V view;

    /**
     * Constructor.
     * @param context
     */
    public Presenter(Context context) {
        this.context = context;
    }

    /**
     * Calling in onCreate(Activity), onActivityCreated(Fragment)
     * @param view
     */
    public void attachView(@NonNull V view) {
        this.view = view;
    }

    /**
     * optional timing calling.
     */
    public void detachView() {
        this.view = null;
    }

    /**
     * Called when presenter is resume.
     * onResume(Activity), onResume(Fragment)
     */
    public abstract void onResume();

    /**
     * Called when presenter is pause.
     * onPause(Activity), onPause(Fragment)
     */
    public abstract void onPause();
}
