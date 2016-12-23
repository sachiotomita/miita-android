package com.naoto.yamaguchi.miita.view.navigationview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.imagefetcher.ImageFetcher;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;

/**
 * Created by naoto on 2016/12/21.
 */

public class MiitaNavigationHeader extends LinearLayout {

    private final Context context;
    private final CurrentUser currentUser;
    private final TextView userIdTextView;
    private final TextView userNameTextView;
    private final ImageView imageView;

    public MiitaNavigationHeader(Context context) {
        this(context, null);
    }

    public MiitaNavigationHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiitaNavigationHeader(Context context, @Nullable AttributeSet attrs,
                                 int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.currentUser = CurrentUser.getInstance();

        View layout = LayoutInflater.from(context).inflate(R.layout.nav_header, this);
        this.userIdTextView = (TextView)layout.findViewById(R.id.nav_user_id_text);
        this.userNameTextView = (TextView)layout.findViewById(R.id.nav_user_name_text);
        this.imageView = (ImageView)layout.findViewById(R.id.nav_user_image);

        this.update();
    }

    private void update() {
        if (this.currentUser.isAuthorize()) {
            final String userId = this.currentUser.getID();
            final String userName = this.currentUser.getName();
            final String imageString = this.currentUser.getImageUrl();

            this.userIdTextView.setText(userId);
            this.userNameTextView.setText(userName);
            ImageFetcher.getInstance().fetch(imageString, this.imageView);
        } else {
            this.userIdTextView.setText(R.string.nav_default_user_id);
            this.userNameTextView.setText(R.string.nav_default_user_name);
            final Drawable drawable =
                    this.context.getDrawable(android.R.drawable.sym_def_app_icon);
            this.imageView.setImageDrawable(drawable);
        }
    }
}
