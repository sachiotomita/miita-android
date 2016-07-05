package com.naoto.yamaguchi.miita.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.entity.BaseItem;
import com.naoto.yamaguchi.miita.task.DownloadImageTask;
import com.naoto.yamaguchi.miita.util.Logger;

import java.util.List;

/**
 * Created by naoto on 16/07/04.
 */
public final class ItemListAdapter<T extends BaseItem> extends ArrayAdapter<T> {

    private Context context;
    private LayoutInflater inflater;
    private ImageView imageView;
    private TextView userIdTextView;
    private TextView titleView;
    private DownloadImageTask downloadImageTask;

    public ItemListAdapter(Context context, List<T> objects) {
        super(context, 0);
        this.addAll(objects);
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO: ViewHolder

        View view = convertView;

        if (convertView == null) {
            view = this.inflater.inflate(R.layout.item_list, null);
        }

        T item = this.getItem(position);
        if (item != null) {
            String userId = item.getUser().getId();
            String title = item.getTitle();
            String imageUrl = item.getUser().getImageUrlString();

            this.userIdTextView = (TextView)view.findViewById(R.id.item_list_user_id_text);
            this.userIdTextView.setText(userId);

            this.titleView = (TextView)view.findViewById(R.id.item_list_title_text);
            this.titleView.setText(title);

            this.imageView = (ImageView)view.findViewById(R.id.item_list_image);
            this.downloadImageTask = new DownloadImageTask(this.context, this.imageView);
            this.downloadImageTask.execute(imageUrl);
        }

        return view;
    }
}
