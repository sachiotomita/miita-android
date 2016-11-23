package com.naoto.yamaguchi.miita.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.entity.ItemTag;
import com.naoto.yamaguchi.miita.entity.base.BaseItem;
import com.naoto.yamaguchi.miita.task.DownloadImageTask;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView Adapter for {@BaseItem}.
 *
 * Created by naoto on 16/07/04.
 */
public final class ItemListAdapter<T extends BaseItem> extends ArrayAdapter<T> {

  final private Context context;
  final private LayoutInflater inflater;
  private DownloadImageTask downloadImageTask;

  public ItemListAdapter(Context context, List<T> objects) {
    super(context, 0);
    this.addAll(objects);
    this.context = context;
    this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    class ViewHolder {
      TextView userIdTextView;
      TextView titleTextView;
      TextView tagTextView;
      ImageView imageView;
    }

    final ViewHolder viewHolder;

    if (convertView == null) {
      convertView = this.inflater.inflate(R.layout.item_list, parent, false);
      viewHolder = new ViewHolder();
      viewHolder.userIdTextView = (TextView)convertView.findViewById(R.id.item_list_user_id_text);
      viewHolder.titleTextView = (TextView)convertView.findViewById(R.id.item_list_title_text);
      viewHolder.tagTextView = (TextView)convertView.findViewById(R.id.item_list_tag_text);
      viewHolder.imageView = (ImageView)convertView.findViewById(R.id.item_list_image);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder)convertView.getTag();
    }

    final T item = this.getItem(position);
    if (item != null) {
      String userId = item.getUser().getId() + "が" + item.getCreatedAtString() + "に投稿しました";
      String title = item.getTitle();
      List<String> tagNameList = new ArrayList<>();
      for (ItemTag tag: item.getTags()) {
        tagNameList.add(tag.getName());
      }
      String tagsString = TextUtils.join(", ", tagNameList);
      String imageUrl = item.getUser().getImageUrlString();

      viewHolder.userIdTextView.setText(userId);
      viewHolder.titleTextView.setText(title);
      viewHolder.tagTextView.setText(tagsString);
      this.downloadImageTask = new DownloadImageTask(this.context, viewHolder.imageView);
      this.downloadImageTask.execute(imageUrl);
    }

    return convertView;
  }
}
