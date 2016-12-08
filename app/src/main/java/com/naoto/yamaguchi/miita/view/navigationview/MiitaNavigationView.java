package com.naoto.yamaguchi.miita.view.navigationview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;

/**
 * Custom NavigationView.
 *
 * Created by naoto on 2016/12/06.
 */

public final class MiitaNavigationView extends NavigationView {

    public MiitaNavigationView(Context context) {
        this(context, null);
    }

    public MiitaNavigationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiitaNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context) {
        // initialize
    }
}
