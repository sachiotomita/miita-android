package com.naoto.yamaguchi.miita.util.share;

import android.app.Activity;
import android.support.v4.app.ShareCompat;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.entity.base.BaseItem;

/**
 * Share {@link BaseItem}, User(feature), Tag(feature).
 *
 * Created by naoto on 2017/01/31.
 */

public final class ShareUtil {

    public static void share(Activity activity, BaseItem item) {
        final String shareBody = "「" + item.getTitle() + "」" + " " + item.getUrlString();

        final ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(activity);
        builder.setChooserTitle(R.string.title_share_item);
        builder.setSubject(item.getTitle());
        builder.setText(shareBody);
        builder.setType("text/plain");
        builder.startChooser();
    }

    private ShareUtil() {}
}
