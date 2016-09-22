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
import com.naoto.yamaguchi.miita.task.DownloadImageTask;

import java.util.List;

/**
 * Created by naoto on 16/07/16.
 */
public final class TagListAdapter<T extends BaseTag> extends ArrayAdapter<T> {

    private Context context;
    private LayoutInflater inflater;
    private ImageView imageView;
    private TextView tagIdTextView;
    private TextView tagCountTextView;
    private DownloadImageTask downloadImageTask;

    public TagListAdapter(Context context, List<T> objects) {
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
            view = this.inflater.inflate(R.layout.tag_list, null);
        }

        T tag = this.getItem(position);
        if (tag != null) {
            String tagId = tag.getId();
            String itemCount = tag.getItemsCount() + " item";
            String imageUrlString = tag.getIconUrlString();

            this.tagIdTextView = (TextView)view.findViewById(R.id.tag_list_id_text);
            this.tagIdTextView.setText(tagId);

            this.tagCountTextView = (TextView)view.findViewById(R.id.tag_list_count_text);
            this.tagCountTextView.setText(itemCount);

            this.imageView = (ImageView)view.findViewById(R.id.tag_list_image);
            this.downloadImageTask = new DownloadImageTask(this.context, this.imageView);
            this.downloadImageTask.execute(imageUrlString);
        }

        return view;
    }
}
