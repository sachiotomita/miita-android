package com.naoto.yamaguchi.miita.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.entity.base.BaseTag;
import com.naoto.yamaguchi.miita.imagefetcher.ImageFetcher;

import java.util.List;

/**
 * ListView Adapter for {@link BaseTag}.
 * <p>
 * Created by naoto on 16/07/16.
 */
public final class TagListAdapter<T extends BaseTag> extends ArrayAdapter<T> {
    private final Context context;
    private final LayoutInflater inflater;

    public TagListAdapter(Context context, List<T> objects) {
        super(context, 0);
        this.addAll(objects);
        this.context = context;
        this.inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final class ViewHolder {
            private TextView tagIdTextView;
            private TextView itemsCountTextView;
            private ImageView imageView;
        }

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.tag_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tagIdTextView =
                    (TextView)convertView.findViewById(R.id.tag_list_id_text);
            viewHolder.itemsCountTextView =
                    (TextView)convertView.findViewById(R.id.tag_list_count_text);
            viewHolder.imageView =
                    (ImageView)convertView.findViewById(R.id.tag_list_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        final T tag = this.getItem(position);
        if (tag != null) {
            String tagId = tag.getId();
            String itemsCount = tag.getItemsCount() + "items";
            String imageUrlString = tag.getIconUrlString();

            viewHolder.tagIdTextView.setText(tagId);
            viewHolder.itemsCountTextView.setText(itemsCount);
            ImageFetcher.getInstance()
                    .setContext(this.context)
                    .fetch(imageUrlString, viewHolder.imageView);
        }

        return convertView;
    }
}
