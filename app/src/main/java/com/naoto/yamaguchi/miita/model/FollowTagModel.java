package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.API;
import com.naoto.yamaguchi.miita.api.Callback;
import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Response;
import com.naoto.yamaguchi.miita.dao.DaoFactory;
import com.naoto.yamaguchi.miita.dao.FollowTagDao;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.FollowTagService;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

import java.util.List;

/**
 * FollowTag Model.
 * <p>
 * Created by naoto on 16/07/12.
 */
public final class FollowTagModel {

    private final Context context;
    private final FollowTagService service;
    private final CurrentUser currentUser;
    private final FollowTagDao dao;
    private OnModelListener<List<FollowTag>> listener;

    public FollowTagModel(Context context) {
        this.context = context;
        this.service = new FollowTagService();
        this.currentUser = CurrentUser.getInstance();
        this.dao = DaoFactory.getFollowTagDao();
    }

    public void request(int page, final RequestType type,
                        OnModelListener<List<FollowTag>> listener) {
        String userId = this.currentUser.getID();
        this.listener = listener;
        this.service
                .setUserId(userId)
                .setPage(page);

        API.request(this.service, new Callback<List<FollowTag>>() {
            @Override
            public void onResponse(Response<List<FollowTag>> response) {
                callSuccessAndProcessResult(type, response);
            }

            @Override
            public void onFailure(HttpException e) {
                callError(e);
            }
        });
    }

    public List<FollowTag> loadTags() {
        return this.dao.findAll();
    }

    public void close() {
        this.dao.close();
    }

    private void callSuccessAndProcessResult(RequestType type,
                                             Response<List<FollowTag>> response) {
        List<FollowTag> realmTags = null;

        switch (type) {
            case FIRST:
            case REFRESH:
                this.dao.truncate();
                realmTags = this.dao.insert(response.result());
                break;
            case PAGING:
                realmTags = this.dao.insert(response.result());
                break;
        }

        if (this.listener != null) {
            this.listener.onSuccess(realmTags);
            this.listener.onComplete();
        }
    }

    private void callError(HttpException e) {
        MiitaException exception = new MiitaException(e.getMessage());
        if (this.listener != null) {
            this.listener.onError(exception);
            this.listener.onComplete();
        }
    }
}
