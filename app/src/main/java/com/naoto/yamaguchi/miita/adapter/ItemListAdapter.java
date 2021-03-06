package com.naoto.yamaguchi.miita.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.entity.ItemTag;
import com.naoto.yamaguchi.miita.entity.base.BaseItem;
import com.naoto.yamaguchi.miita.imagefetcher.ImageFetcher;
import com.naoto.yamaguchi.miita.task.DownloadImageTask;
import com.naoto.yamaguchi.miita.view.tagview.MiitaTagView;
import com.naoto.yamaguchi.miita.view.tagview.TagClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView Adapter for {@BaseItem}.
 * <p>
 * Created by naoto on 16/07/04.
 */
public final class ItemListAdapter<T extends BaseItem> extends ArrayAdapter<T> {

    final private Context context;
    final private LayoutInflater inflater;
    final private TagClickListener tagClickListener;

    public ItemListAdapter(Context context, List<T> objects, TagClickListener tagClickListener) {
        super(context, 0);
        this.addAll(objects);
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.tagClickListener = tagClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        class ViewHolder {
            private TextView userIdTextView;
            private TextView createdTextView;
            private TextView titleTextView;
            private FlexboxLayout tagFlexbox;
            private ImageView imageView;
        }

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.userIdTextView = (TextView) convertView.findViewById(R.id.item_list_user_id_text);
            viewHolder.createdTextView = (TextView) convertView.findViewById(R.id.item_list_created_text);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.item_list_title_text);
            viewHolder.tagFlexbox = (FlexboxLayout) convertView.findViewById(R.id.item_list_tag_flexbox);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.item_list_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final T item = this.getItem(position);
        if (item != null) {
            final String userId = item.getUser().getId();
            final String created = item.getCreatedAtString() + "に投稿しました";
            final String title = item.getTitle();
            final String imageUrl = item.getUser().getImageUrlString();

            viewHolder.userIdTextView.setText(userId);
            viewHolder.createdTextView.setText(created);
            viewHolder.titleTextView.setText(title);

            viewHolder.tagFlexbox.removeAllViews();
            for (final ItemTag tag: item.getTags()) {
                final MiitaTagView tagView = new MiitaTagView(this.context);
                tagView.setTitle(tag.getName());
                tagView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tagClick(tag);
                    }
                });
                viewHolder.tagFlexbox.addView(tagView);
            }

            ImageFetcher.getInstance()
                    .setContext(this.context)
                    .fetch(imageUrl, viewHolder.imageView);
        }

        return convertView;
    }

    private void tagClick(ItemTag tag) {
        if (this.tagClickListener != null) {
            this.tagClickListener.onTagClick(tag);
        }
    }
}
