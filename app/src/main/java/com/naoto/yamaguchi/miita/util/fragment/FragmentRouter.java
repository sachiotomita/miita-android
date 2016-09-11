package com.naoto.yamaguchi.miita.util.fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.naoto.yamaguchi.miita.util.Logger;

/**
 * Created by naoto on 16/09/04.
 */
public final class FragmentRouter {

    private Logger logger;
    private FragmentManager manager;
    private Fragment fragment;
    private @IdRes int viewId;
    private boolean isAddStack;

    public static FragmentRouter newInstance() {
        FragmentRouter router = new FragmentRouter();
        return router;
    }

    private FragmentRouter() {
        this.isAddStack = false;
        this.logger = Logger.getInstance();
    }

    public <T extends Fragment> FragmentRouter begin(FragmentManager manager, T fragment) {
        this.fragment = fragment;
        this.manager = manager;
        return this;
    }

    public FragmentRouter replace(@IdRes int viewId) {
        this.viewId = viewId;
        return this;
    }

    public FragmentRouter addStack(boolean addStack) {
        this.isAddStack = addStack;
        return this;
    }

    public FragmentRouter commit() {
        if (this.isAddStack) {
            this.manager.beginTransaction()
                    .replace(this.viewId, this.fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            this.manager.beginTransaction()
                    .replace(this.viewId, this.fragment)
                    .commit();
        }

        return this;
    }
}
