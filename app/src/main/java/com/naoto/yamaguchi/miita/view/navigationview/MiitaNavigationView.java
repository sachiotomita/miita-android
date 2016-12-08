package com.naoto.yamaguchi.miita.view.navigationview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.view.MenuItem;

/**
 * Custom NavigationView.
 *
 * Created by naoto on 2016/12/06.
 */

public final class MiitaNavigationView extends NavigationView {
    private final Context context;
    private NavigationMenuType currentMenu;

    public MiitaNavigationView(Context context) {
        this(context, null);
    }

    public MiitaNavigationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiitaNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.init(context);
    }

    private void init(Context context) {
        this.currentMenu = NavigationMenuType.ALL_ITEM;
    }

    public void setSelected(NavigationMenuType menuType) {
        this.getMenu()
                .getItem(menuType.toInt())
                .setChecked(true);
    }

    public void selectedMenu() {
        // noop
    }
}
