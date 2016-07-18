package com.naoto.yamaguchi.miita.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.activity.HomeActivity;
import com.naoto.yamaguchi.miita.adapter.ItemListAdapter;
import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.model.TagItemModel;
import com.naoto.yamaguchi.miita.util.RequestType;

import java.util.List;

public class TagItemFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener {

    public interface OnItemClickListener {
        void onItemClick(Item item);
    }

    private OnItemClickListener listener;
    private TagItemModel model;
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

        this.model = new TagItemModel(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tag_item, container, false);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_follow_tag);

        this.refreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_widget);
        this.refreshLayout.setOnRefreshListener(this);

        this.listView = (ListView)rootView.findViewById(R.id.listView);
        this.listView.setOnScrollListener(this);
        this.listView.setOnItemClickListener(this);

        this.adapter = new ItemListAdapter<>(this.getContext(), this.items);
        this.listView.setAdapter(this.adapter);

        this.spinner = (ProgressBar)rootView.findViewById(R.id.progress_bar);
        this.spinner.setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.request(RequestType.FIRST);
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
        this.request(RequestType.REFRESH);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {}

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // TODO: per page
        if (totalItemCount < (30 * this.model.getPage())) {
            return;
        }

        if (this.model.isPaging()) {
            return;
        }

        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            this.request(RequestType.PAGING);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
        if (this.listener == null) {
            return;
        }

        ListView listView = (ListView)parent;
        if (listView.getId() == R.id.listView) {
            Item item = (Item)listView.getItemAtPosition(position);
            this.listener.onItemClick(item);
        }
    }

    private void request(RequestType type) {
        switch (type) {
            case FIRST:
                this.listView.setVisibility(View.GONE);
                this.spinner.setVisibility(View.VISIBLE);
                this.model.request(RequestType.FIRST, this.getListener(RequestType.FIRST));
                break;
            case REFRESH:
                this.refreshLayout.setEnabled(false);
                this.model.request(RequestType.REFRESH, this.getListener(RequestType.REFRESH));
                break;
            case PAGING:
                this.footerView = getActivity().getLayoutInflater().inflate(R.layout.progress_footer, null);
                this.listView.addFooterView(this.footerView);
                this.model.request(RequestType.PAGING, this.getListener(RequestType.PAGING));
                break;
        }
    }

    private TagItemModel.OnRequestListener getListener(final RequestType type) {
        return new TagItemModel.OnRequestListener() {
            @Override
            public void onSuccess(List<Item> results) {
                notifyDataSetChanged(type, results);
            }

            @Override
            public void onError(APIException e) {

            }

            @Override
            public void onComplete() {
                invalidateView(type);
            }
        };
    }

    private void notifyDataSetChanged(RequestType type, List<Item> items) {
        switch (type) {
            case FIRST:
            case REFRESH:
                this.adapter.clear();
                this.adapter.addAll(items);
                break;
            case PAGING:
                this.adapter.addAll(items);
                break;
        }
        this.adapter.notifyDataSetChanged();
    }

    private void invalidateView(RequestType type) {
        switch (type) {
            case FIRST:
                this.listView.setVisibility(View.VISIBLE);
                this.spinner.setVisibility(View.GONE);
                break;
            case REFRESH:
                this.refreshLayout.setEnabled(true);
                this.refreshLayout.setRefreshing(false);
                break;
            case PAGING:
                this.listView.removeFooterView(this.footerView);
                break;
        }
    }

}
