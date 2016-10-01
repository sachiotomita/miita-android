package com.naoto.yamaguchi.miita.presenter.base;

/**
 * Presenter LifeCycle.
 *
 * Created by naoto on 2016/10/01.
 */

public abstract class Presenter {

    /**
     * Called when presenter is init.
     */
    public abstract void initialize();

    /**
     * Called when presenter is resume.
     */
    public abstract void onResume();

    /**
     * Called when presenter is pause
     */
    public abstract void onPause();
}
