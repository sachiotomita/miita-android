package com.naoto.yamaguchi.miita.view.navigationview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.fragment.AllItemFragment;
import com.naoto.yamaguchi.miita.fragment.FollowTagFragment;
import com.naoto.yamaguchi.miita.fragment.StockItemFragment;

/**
 * {@MiitaNavigationView} Menu Type.
 *
 * TODO Later:
 * - http://qiita.com/KeithYokoma/items/51852d343002d969d841
 * - http://qiita.com/wasnot/items/dfc33bb91f64abfec04c
 *
 * Created by naoto on 2016/12/09.
 */

public enum NavigationMenuType {
    ALL_ITEM(ActionType.TO_FRAGMENT, R.id.nav_all_item,
            AllItemFragment.class.getSimpleName()) {
        @Nullable
        @Override
        public Fragment getFragment() {
            return AllItemFragment.newInstance();
        }
    },
    STOCK_ITEM(ActionType.TO_FRAGMENT, R.id.nav_stock_item,
            StockItemFragment.class.getSimpleName()) {
        @Nullable
        @Override
        public Fragment getFragment() {
            return StockItemFragment.newInstance();
        }
    },
    FOLLOW_TAG(ActionType.TO_FRAGMENT, R.id.nav_follow_tag,
            FollowTagFragment.class.getSimpleName()) {
        @Nullable
        @Override
        public Fragment getFragment() {
            return FollowTagFragment.newInstance();
        }
    },
    SETTING(ActionType.TO_ACTIVITY, R.id.nav_setting, null) {
        @Nullable
        @Override
        public Fragment getFragment() {
            return null;
        }
    };

    // NOTE: if all transition be transition, delete this type.
    public enum ActionType {
        TO_FRAGMENT,
        TO_ACTIVITY
    }

    private final ActionType actionType;
    private final int menuId;
    private final String fragmentName;

    NavigationMenuType(ActionType type, int menuId, @Nullable String fragmentName) {
        this.actionType = type;
        this.menuId = menuId;
        this.fragmentName = fragmentName;
    }

    public static NavigationMenuType fromMenuId(MenuItem item) {
        final int id = item.getItemId();
        for (NavigationMenuType menu: values()) {
            if (menu.menuId == id) {
                return menu;
            }
        }
        throw new AssertionError("Not Found NavigationMenu from MenuItem Id.");
    }

    public static NavigationMenuType fromName(Fragment fragment) {
        final String name = fragment.getClass().getSimpleName();
        for (NavigationMenuType menu: values()) {
            if (menu.actionType == ActionType.TO_ACTIVITY) {
                continue;
            }

            if (menu.fragmentName.equals(name)) {
                return menu;
            }
        }
        throw new AssertionError("Not Found NavigationMenu from Fragment name");
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public int getMenuId() {
        return this.menuId;
    }

    public String getFragmentName() {
        return this.fragmentName;
    }

    @Nullable
    public abstract Fragment getFragment();
}
