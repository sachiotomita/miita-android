package com.naoto.yamaguchi.miita.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.adapter.ItemListAdapter;
import com.naoto.yamaguchi.miita.entity.ui.Item;
import com.naoto.yamaguchi.miita.entity.ui.ItemTag;
import com.naoto.yamaguchi.miita.presenter.TagItemPresenter;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;
import com.naoto.yamaguchi.miita.util.preference.PerPage;
import com.naoto.yamaguchi.miita.view.tagview.TagClickListener;

import java.util.ArrayList;
import java.util.List;

public class TagItemFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener,
        TagItemPresenter.View,
        TagClickListener {

    public interface OnItemClickListener {

        void onItemClick(Item item, Pair<View, String>... sharedElements);

        void onTagClick(ItemTag itemTag);
    }

    private OnItemClickListener listener;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private View footerView;
    private ProgressBar spinner;
    private ItemListAdapter<Item> adapter;

    private final TagItemPresenter presenter;

    public TagItemFragment() {
        this.presenter = new TagItemPresenter(this.getContext());
    }

    public static TagItemFragment newInstance(String tagId) {
        TagItemFragment fragment = new TagItemFragment();

        Bundle args = new Bundle();
        args.putString("tag_id", tagId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            String tagId = getArguments().getString("tag_id");
            this.presenter.setTagId(tagId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tag_item, container, false);

        this.refreshLayout =
                (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_widget);
        this.refreshLayout.setOnRefreshListener(this);

        this.listView = (ListView) rootView.findViewById(R.id.listView);
        this.listView.setOnScrollListener(this);
        this.listView.setOnItemClickListener(this);
        ViewCompat.setNestedScrollingEnabled(this.listView, true);

        this.adapter = new ItemListAdapter<>(this.getContext(), new ArrayList<Item>(), this);
        this.listView.setAdapter(this.adapter);

        this.spinner = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        this.spinner.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.presenter.attachView(this);
        this.presenter.loadItems();
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

    @Override
    public void onRefresh() {
        this.presenter.refreshItems();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        String perPage = PerPage.get();
        if (totalItemCount < (Integer.parseInt(perPage) * this.presenter.getPage())) {
            return;
        }

        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            this.presenter.nextLoadItems();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        if (this.listener == null) {
            return;
        }

        ListView listView = (ListView) parent;
        if (listView.getId() == R.id.listView) {
            Item item = (Item) listView.getItemAtPosition(position);
            final TextView titleTextView = (TextView)view.findViewById(R.id.item_list_title_text);
            final ImageView imageView = (ImageView)view.findViewById(R.id.item_list_image);
            final TextView userIdTextView = (TextView)view.findViewById(R.id.item_list_user_id_text);
            final TextView createdTextView = (TextView)view.findViewById(R.id.item_list_created_text);

            final Pair<View, String> pair1 = new Pair<View, String>(titleTextView,
                    getString(R.string.transition_title_item_list_to_item));
            final Pair<View, String> pair2 = new Pair<View, String>(imageView,
                    getString(R.string.transition_image_item_list_to_item));
            final Pair<View, String> pair3 = new Pair<View, String>(userIdTextView,
                    getString(R.string.transition_user_id_item_list_to_item));
            final Pair<View, String> pair4 = new Pair<View, String>(createdTextView,
                    getString(R.string.transition_created_item_list_to_item));

            this.listener.onItemClick(item, pair1, pair2, pair3, pair4);
        }
    }

    @Override
    public void onTagClick(ItemTag tag) {
        if (this.listener != null) {
            this.listener.onTagClick(tag);
        }
    }

    @Override
    public void showLoading() {
        this.spinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.spinner.setVisibility(View.GONE);
    }

    @Override
    public void showListView() {
        this.listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListView() {
        this.listView.setVisibility(View.GONE);
    }

    @Override
    public void beginRefreshing() {
        this.refreshLayout.setEnabled(false);
    }

    @Override
    public void endRefreshing() {
        this.refreshLayout.setEnabled(true);
        this.refreshLayout.setRefreshing(false);
    }

    @Override
    public void addFooterView() {
        this.footerView =
                getActivity().getLayoutInflater().inflate(R.layout.progress_footer, null);
        this.listView.addFooterView(this.footerView);
    }

    @Override
    public void removeFooterView() {
        this.listView.removeFooterView(this.footerView);
    }

    @Override
    public void reloadData(boolean forceUpdate, List<Item> items) {
        if (forceUpdate) {
            this.adapter.clear();
        }

        this.adapter.addAll(items);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(MiitaException e) {
        // TODO: show alert
    }
}
