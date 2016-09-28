package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.mapper.TagListObjectMapper;
import com.naoto.yamaguchi.miita.service.base.BaseService;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

import java.util.List;

/**
 * Created by naoto on 16/07/12.
 */
public final class FollowTagService extends BaseService<List<FollowTag>> {

    private String userId;

    public FollowTagService(Context context) {
        super(context);
    }

    @Override
    protected String getMethod() {
        return "GET";
    }

    @Override
    protected byte[] getBody() {
        return null;
    }

    @Override
    protected String getPath() {
        return "/users/" + this.userId + "/following_tags";
    }

    @Override
    protected int getPage() {
        return this.page;
    }

    @Override
    protected boolean isPerPage() {
        return true;
    }

    @Override
    protected boolean isResponse() {
        return true;
    }

    @Override
    protected List<FollowTag> getResponse(String json) throws APIException {
        try {
            return TagListObjectMapper.map(FollowTag.class, json);
        } catch (APIException e) {
            throw e;
        }
    }

    public void request(int page, String userId, OnRequestListener<List<FollowTag>> listener) {
        this.page = page;
        this.userId = userId;
        super.request(listener);
    }
}
