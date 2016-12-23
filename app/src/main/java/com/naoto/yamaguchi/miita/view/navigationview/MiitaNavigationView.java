package com.naoto.yamaguchi.miita.view.navigationview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.view.View;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;

/**
 * Custom NavigationView.
 *
 * Created by naoto on 2016/12/06.
 */

public final class MiitaNavigationView extends NavigationView {
    private static final int INDEX_HEADER = 0;

    private final Context context;
    private final CurrentUser currentUser;
    private final MiitaNavigationHeader header;

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

        View headerLayout = this.getHeaderView(INDEX_HEADER);
        this.header = (MiitaNavigationHeader)headerLayout.findViewById(R.id.nav_header_view);
    }

    public void setCheckedItem(NavigationMenuType menuType) {
        this.setCheckedItem(menuType.getMenuIdRes());
    }

    public void updateHeader() {
        this.header.update();
    }
}
