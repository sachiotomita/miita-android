package com.naoto.yamaguchi.miita.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by naoto on 16/08/24.
 */
public final class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // TODO: 調べる

        // NOTE:
        // 1. FeedBack http://blog.excite.co.jp/spdev/20711466/
        // 2. 取得件数
        // 3. LICENSE Intent
        // 4. Version (No Action)
        // addPreferencesFromResource();


    }
}
