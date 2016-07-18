package com.naoto.yamaguchi.miita.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.adapter.ItemListAdapter;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.model.AllItemModel;

import java.util.List;

public class TagItemFragment extends Fragment {

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    private OnItemClickListener listener;
    private AllItemModel model;
    private List<Item> items;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private View footerView;
    private ProgressBar spinner;
    private ItemListAdapter<Item> adapter;

    public TagItemFragment() {}

    public static TagItemFragment newInstance() {
        TagItemFragment fragment = new TagItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tag_item, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListener) {
            this.listener = (OnItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

}
