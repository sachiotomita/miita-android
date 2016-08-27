package com.naoto.yamaguchi.miita.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.naoto.yamaguchi.miita.R;

/**
 * Created by naoto on 16/08/24.
 */
public final class SettingsFragment extends PreferenceFragment {

    private static final String PER_PAGE_KEY = "pref_key_per_page";
    private static final String DEFAULT_VALUE_PER_PAGE = "30";

    private ListPreference perPagePref;

    // NOTE:
    // 1. FeedBack http://blog.excite.co.jp/spdev/20711466/
    // 2. 取得件数
    // 3. LICENSE Intent
    // 4. Version (No Action)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO:
        // logoutのlistを表示するために、xmlを2種類用意する
        addPreferencesFromResource(R.xml.pref_all);
        setHasOptionsMenu(true); // TODO: 調べる
        this.init();
    }

    private void init() {

        this.perPagePref = (ListPreference)findPreference(PER_PAGE_KEY);
        this.perPagePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (newValue != null) {
                    preference.setSummary((CharSequence)newValue);
                    return true;
                }
                return false;
            }
        });

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String perPage = sharedPreferences.getString(PER_PAGE_KEY, DEFAULT_VALUE_PER_PAGE);
        this.perPagePref.setSummary(perPage + "件");
    }
}
