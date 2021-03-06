package com.naoto.yamaguchi.miita.fragment;

import android.content.Context;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.observer.MiitaEventObject;
import com.naoto.yamaguchi.miita.observer.MiitaObservable;
import com.naoto.yamaguchi.miita.util.device.Device;
import com.naoto.yamaguchi.miita.util.preference.PerPage;
import com.naoto.yamaguchi.miita.util.preference.PreferencesConstants;

/**
 * Setting Fragment.
 *
 * Created by naoto on 16/08/24.
 */
public final class SettingsFragment extends PreferenceFragment {

    public interface OnPreferenceClickListener {
        // TODO: use ENUM
        void onLicensePrefClick();
    }

    private ListPreference perPagePref;
    private Preference licensePref;
    private Preference logoutPref;
    private Preference versionPref;
    private CurrentUser currentUser;
    private OnPreferenceClickListener listener;

    // TODO: create feed back page.

    // NOTE:
    // 1. 取得件数
    // 2. LICENSE Intent
    // 3. Logout (ログインしてたら。)
    // 4. Version (No Action)

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    public SettingsFragment() {
        this.currentUser = CurrentUser.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true); // TODO: 調べる

        this.initView();
        this.loadPref();
        this.init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPreferenceClickListener) {
            this.listener = (OnPreferenceClickListener)context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPreferenceClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    private void initView() {
        // NOOP
    }

    private void loadPref() {
        if (this.currentUser.isAuthorize()) {
            addPreferencesFromResource(R.xml.pref_all_auth);
        } else {
            addPreferencesFromResource(R.xml.pref_all_not_auth);
        }
    }

    private void init() {
        this.perPagePref =
                (ListPreference) findPreference(PreferencesConstants.PER_PAGE_KEY);
        this.perPagePref.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference,
                                                      Object newValue) {
                        if (newValue != null) {
                            preference.setSummary((CharSequence) newValue);
                            return true;
                        }
                        return false;
                    }
                });

        String perPage = PerPage.get();
        this.perPagePref.setSummary(perPage + "件");

        this.licensePref = findPreference(PreferencesConstants.LICENSE_KEY);
        this.licensePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                listener.onLicensePrefClick();
                return true;
            }
        });

        if (this.currentUser.isAuthorize()) {
            this.logoutPref = findPreference(PreferencesConstants.LOGOUT_KEY);
            this.logoutPref.setOnPreferenceClickListener(
                    new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            currentUser.logout();

                            final MiitaEventObject eventObject = new MiitaEventObject(
                                    getActivity(), MiitaEventObject.Type.LOGOUT);
                            MiitaObservable.getInstance().notify(eventObject);

                            getActivity().finish();
                            return true;
                        }
                    });
        }

        this.versionPref = findPreference(PreferencesConstants.VERSION_KEY);
        final String title = this.versionTitle(this.versionPref);
        this.versionPref.setTitle(title);
    }

    private String versionTitle(Preference versionPref) {
        final String title = versionPref.getTitle().toString();
        final String space = " ";

        return title + space + Device.getAppVersionName();
    }
}
