package com.naoto.yamaguchi.miita.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.util.SharedPreferencesUtil;

/**
 * Created by naoto on 16/08/24.
 */
public final class SettingsFragment extends PreferenceFragment {

    private static final String PER_PAGE_KEY = "pref_key_per_page";
    private static final String LOGOUT_KEY = "pref_key_logout";
    private static final String DEFAULT_VALUE_PER_PAGE = "30";

    private ListPreference perPagePref;
    private Preference logoutPref;
    private CurrentUser currentUser;

    // NOTE:
    // 1. FeedBack http://blog.excite.co.jp/spdev/20711466/
    // 2. 取得件数
    // 3. LICENSE Intent
    // 4. Logout (ログインしてたら。)
    // 5. Version (No Action)

    public SettingsFragment() {
        this.currentUser = CurrentUser.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // TODO: 調べる
        this.loadPref();
        this.init();
    }

    private void loadPref() {
        if (this.currentUser.isAuthorize(getActivity())) {
            addPreferencesFromResource(R.xml.pref_all_auth);
        } else {
            addPreferencesFromResource(R.xml.pref_all_not_auth);
        }
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

        String perPage =
                SharedPreferencesUtil.getString(getActivity(), PER_PAGE_KEY, DEFAULT_VALUE_PER_PAGE);
        this.perPagePref.setSummary(perPage + "件");

        if (this.currentUser.isAuthorize(getActivity())) {
            this.logoutPref = (Preference)findPreference(LOGOUT_KEY);
            this.logoutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    currentUser.logout(getActivity());
                    // TODO: HomeActivityに値を渡したい
                    getActivity().finish();
                    return true;
                }
            });
        }
    }
}
