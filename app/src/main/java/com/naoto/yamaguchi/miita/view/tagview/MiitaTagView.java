package com.naoto.yamaguchi.miita.view.tagview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.naoto.yamaguchi.miita.R;

/**
 * Tag View in Item List, Item Header, etc,,,
 *
 * Created by naoto on 2017/01/21.
 */

public final class MiitaTagView extends LinearLayout {

    private final Context context;
    private final TextView tagTextView;

    public MiitaTagView(Context context) {
        this(context, null);
    }

    public MiitaTagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiitaTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        View layout = LayoutInflater.from(context).inflate(R.layout.view_tag, this);
        this.tagTextView = (TextView)layout.findViewById(R.id.tag_text);
    }

    public void setTitle(String title) {
        this.tagTextView.setText(title);
    }
}
