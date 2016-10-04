package com.naoto.yamaguchi.miita.fragment;

import android.content.Context;
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
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.presenter.AllItemPresenter;
import com.naoto.yamaguchi.miita.util.preference.PerPage;

import java.util.ArrayList;
import java.util.List;

public class AllItemFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener, AllItemPresenter.View {

    public interface OnItemClickListener {
        void onItemClick(AllItem item);
    }

    private OnItemClickListener listener;
    private List<AllItem> items;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private View footerView;
    private ProgressBar spinner;
    private ItemListAdapter<AllItem> adapter;

    private AllItemPresenter presenter;

    public AllItemFragment() {}

    public static AllItemFragment newInstance() {
        AllItemFragment fragment = new AllItemFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.items = new ArrayList<>();
        this.presenter = new AllItemPresenter(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_item, container, false);

        ((HomeActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_all_item);

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
    public void onScrollStateChanged(AbsListView absListView, int i) {}

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        String perPage = PerPage.get(this.getContext());
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

        ListView listView = (ListView)parent;
        if (listView.getId() == R.id.listView) {
            AllItem item = (AllItem)listView.getItemAtPosition(position);
            this.listener.onItemClick(item);
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
    public void reloadData(List<AllItem> items) {
        this.adapter.clear();
        this.adapter.addAll(items);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(APIException e) {
        // TODO: show error
    }
}
