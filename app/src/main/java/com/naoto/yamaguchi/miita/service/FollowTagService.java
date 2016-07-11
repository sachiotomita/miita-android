package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.mapper.TagListObjectMapper;

import java.util.List;

/**
 * Created by naoto on 16/07/12.
 */
public final class FollowTagService extends DeliverResponseService<List<FollowTag>> {

    public interface OnRequestListener {
        void onSuccess(List<FollowTag> results);
        void onError(APIException e);
    }

    private int page;
    private String userId;
    private OnRequestListener listener;

    public FollowTagService(Context context) {
        super(context);
        this.page = 1;
    }

    @Override
    protected String getMethod() {
        return "GET";
    }

    @Override
    protected String getUrlString() {
        return APIURLBuilder.build(this.getPath(), this.page);
    }

    @Override
    protected byte[] getBody() {
        return null;
    }

    @Override
    protected String getPath() {
        return "/users/" + this.userId + "/following_tags";
    }

    public void request(int page, String userId, OnRequestListener listener) {
        this.page = page;
        this.userId = userId;
        this.addRequestListener(listener);
        super.request();
    }

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<FollowTag> getResponse(String jsonString) throws APIException {
        try {
            return TagListObjectMapper.map(FollowTag.class, jsonString);
        } catch (APIException e) {
            throw e;
        }
    }

    @Override
    protected void deliverSuccess(List<FollowTag> results) {
        if (this.listener != null) {
            this.listener.onSuccess(results);
        }
    }

    @Override
    protected void deliverError(APIException e) {
        if (this.listener != null) {
            this.listener.onError(e);
        }
    }
}
