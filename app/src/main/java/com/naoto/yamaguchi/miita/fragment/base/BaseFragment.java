package com.naoto.yamaguchi.miita.fragment.base;

import android.support.v4.app.Fragment;

/**
 * Created by naoto on 16/09/04.
 */
public interface BaseFragment<T extends Fragment> {
    T newInstance();
}
