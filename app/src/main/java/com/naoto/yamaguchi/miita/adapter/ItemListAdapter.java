package com.naoto.yamaguchi.miita.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.naoto.yamaguchi.miita.entity.BaseItem;

import java.util.List;

/**
 * Created by naoto on 16/07/04.
 */
public final class ItemListAdapter<T extends BaseItem> extends ArrayAdapter<T> {

    public ItemListAdapter(Context context, List<T> objects) {
        super(context, 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
