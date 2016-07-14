package com.naoto.yamaguchi.miita.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naoto.yamaguchi.miita.R;
import com.naoto.yamaguchi.miita.entity.FollowTag;

public class FollowTagFragment extends Fragment {

    public interface OnTagClickListener {
        void onTagClick(FollowTag tag);
    }

    private static final String ARG_FORCE_UPDATE = "force_update";

    private OnTagClickListener listener;
    private String forceUpdate;

    public FollowTagFragment() {}

    public static FollowTagFragment newInstance(String forceUpdate) {
        FollowTagFragment fragment = new FollowTagFragment();

        // FIXME: for example
        Bundle args = new Bundle();
        args.putString(ARG_FORCE_UPDATE, forceUpdate);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.forceUpdate = getArguments().getString(ARG_FORCE_UPDATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow_tag, container, false);
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

}
