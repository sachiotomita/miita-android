package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.service.FollowTagService;

import java.util.List;

/**
 * Created by naoto on 16/07/12.
 */
public final class FollowTagModel {

    public interface OnRequestListener {
        void onSuccess(List<FollowTag> results);
        void onError(APIException e);
        void onComplete();
    }

    private Context context;
    private int page;
    private boolean isPaging;
    private OnRequestListener listener;
    private FollowTagService service;

    public FollowTagModel(Context context) {
        this.context = context;
        this.page = 1;
        this.isPaging = false;
        this.service = new FollowTagService(this.context);

    }



}
