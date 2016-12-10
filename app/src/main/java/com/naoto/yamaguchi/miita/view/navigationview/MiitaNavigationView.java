package com.naoto.yamaguchi.miita.view.navigationview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.MenuItem;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.activity.HomeActivity;
import com.naoto.yamaguchi.miita.activity.SettingsActivity;
import com.naoto.yamaguchi.miita.fragment.AllItemFragment;
import com.naoto.yamaguchi.miita.fragment.FollowTagFragment;
import com.naoto.yamaguchi.miita.fragment.StockItemFragment;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.util.fragment.FragmentRouter;
import com.naoto.yamaguchi.miita.view.alert.MiitaAlertDialogBuilder;
import com.naoto.yamaguchi.miita.view.alert.MiitaAlertDialogType;

/**
 * Custom NavigationView.
 *
 * Created by naoto on 2016/12/06.
 */

public final class MiitaNavigationView extends NavigationView {
    private final Context context;
    private final CurrentUser currentUser;
    private NavigationMenuType currentMenu; // TODO: delete

    public MiitaNavigationView(Context context) {
        this(context, null);
    }

    public MiitaNavigationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiitaNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.currentUser = CurrentUser.getInstance();
        this.init(context);
    }

    private void init(Context context) {
        // NOOP
    }

    public void setSelected(NavigationMenuType menuType) {
        this.getMenu()
                .getItem(menuType.getMenuIdRes())
                .setChecked(true);
    }

    public void selectedMenu(MenuItem item, HomeActivity activity) {
        final int itemId = item.getItemId();
        final boolean isStack = !item.isChecked();
        final FragmentManager manager = activity.getSupportFragmentManager();

        switch (itemId) {
            case R.id.nav_all_item:
                this.transitionFragment(manager, AllItemFragment.newInstance(), isStack);
                break;
            case R.id.nav_stock_item:
                if (this.currentUser.isAuthorize()) {
                    this.transitionFragment(manager, StockItemFragment.newInstance(),
                            isStack);
                } else {
                    this.showLoginAlert();
                }
                break;
            case R.id.nav_follow_tag:
                if (this.currentUser.isAuthorize()) {
                    this.transitionFragment(manager, FollowTagFragment.newInstance(),
                            isStack);
                } else {
                    this.showLoginAlert();
                }
                break;
            case R.id.nav_setting:
                Intent intent = new Intent(this.context, SettingsActivity.class);
                activity.startActivity(intent);
                break;
        }
    }

    private void transitionFragment(FragmentManager manager, Fragment fragment,
                                    boolean isStack) {
        FragmentRouter.newInstance()
                .begin(manager, fragment)
                .replace(R.id.home_container_view)
                .addStack(isStack)
                .commit();
    }

    private void showLoginAlert() {
        new MiitaAlertDialogBuilder(this.context)
                .setType(MiitaAlertDialogType.LOGIN)
                .build()
                .show();
    }
}
