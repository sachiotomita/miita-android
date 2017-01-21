package com.naoto.yamaguchi.miita.view.tagview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Tag View in Item List, Item Header, etc,,,
 *
 * Created by naoto on 2017/01/21.
 */

public final class MiitaTagView extends LinearLayout {

    private final Context context;

    public MiitaTagView(Context context) {
        this(context, null);
    }

    public MiitaTagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiitaTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
}
