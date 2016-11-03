package com.naoto.yamaguchi.miita.util.preference;

/**
 * Request item per page.
 * Default 30.
 *
 * Created by naoto on 2016/09/25.
 */

public final class PerPage {
  public static String get() {
    return SharedPreferencesUtil.getString(
            PreferencesConstants.PER_PAGE_KEY,
            PreferencesConstants.PER_PAGE_DEFAULT_VALUE);
  }

  public static void set(String value) {
    SharedPreferencesUtil.setString(
            PreferencesConstants.PER_PAGE_KEY,
            value);
  }

  public static void delete() {
    SharedPreferencesUtil.deleteString(PreferencesConstants.PER_PAGE_KEY);
  }
}
