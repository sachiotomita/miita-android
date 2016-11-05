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
import com.naoto.yamaguchi.miita.adapter.TagListAdapter;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.presenter.FollowTagPresenter;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;
import com.naoto.yamaguchi.miita.util.preference.PerPage;

import java.util.ArrayList;
import java.util.List;

public class FollowTagFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener,
        AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener,
        FollowTagPresenter.View {

  public interface OnTagClickListener {
    void onTagClick(FollowTag tag);
  }

  private static final String ARG_FORCE_UPDATE = "force_update";

  private ListView listView;
  private SwipeRefreshLayout refreshLayout;
  private View footerView;
  private ProgressBar spinner;

  private OnTagClickListener listener;
  private String forceUpdate;
  private List<FollowTag> tags;
  private TagListAdapter<FollowTag> adapter;
  private final FollowTagPresenter presenter;

  public FollowTagFragment() {
    this.tags = new ArrayList<>();
    this.presenter = new FollowTagPresenter(this.getContext());
  }

  public static FollowTagFragment newInstance(/**String forceUpdate**/) {
    FollowTagFragment fragment = new FollowTagFragment();

    // FIXME: for example
    Bundle args = new Bundle();
    // args.putString(ARG_FORCE_UPDATE, forceUpdate);
    fragment.setArguments(args);

    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // FIXME: for example
    if (getArguments() != null) {
      // this.forceUpdate = getArguments().getString(ARG_FORCE_UPDATE);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_follow_tag, container, false);

    ((HomeActivity)getActivity()).getSupportActionBar().setTitle(R.string.title_follow_tag);

    this.refreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_widget);
    this.refreshLayout.setOnRefreshListener(this);

    this.listView = (ListView)rootView.findViewById(R.id.listView);
    this.listView.setOnScrollListener(this);
    this.listView.setOnItemClickListener(this);

    this.adapter = new TagListAdapter<>(this.getContext(), this.tags);
    this.listView.setAdapter(this.adapter);

    this.spinner = (ProgressBar)rootView.findViewById(R.id.progress_bar);
    this.spinner.setVisibility(View.GONE);

    return rootView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    this.presenter.attachView(this);
    this.presenter.loadTags();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnTagClickListener) {
      this.listener = (OnTagClickListener) context;
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
    this.presenter.refreshTags();
  }

  @Override
  public void onScrollStateChanged(AbsListView absListView, int i) {}

  @Override
  public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    String perPage = PerPage.get();
    if (totalItemCount < (Integer.parseInt(perPage) * this.presenter.getPage())) {
      return;
    }

    if (firstVisibleItem + visibleItemCount == totalItemCount) {
      this.presenter.nextLoadTags();
    }
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
    if (this.listener == null) {
      return;
    }

    ListView listView = (ListView)parent;
    if (listView.getId() == R.id.listView) {
      FollowTag tag = (FollowTag)listView.getItemAtPosition(position);
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
  public void reloadData(List<FollowTag> tags) {
    this.adapter.clear();
    this.adapter.addAll(tags);
    this.adapter.notifyDataSetChanged();
  }

  @Override
  public void showError(MiitaException e) {
    // TODO: show alert
  }
}
