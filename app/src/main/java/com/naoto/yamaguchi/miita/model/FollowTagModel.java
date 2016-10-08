package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.ex_api.APIException;
import com.naoto.yamaguchi.miita.dao.DaoFactory;
import com.naoto.yamaguchi.miita.dao.FollowTagDao;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.model.base.BaseModel;
import com.naoto.yamaguchi.miita.model.base.UsingDaoModelType;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.FollowTagService;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

import java.util.List;

/**
 * Created by naoto on 16/07/12.
 */
public final class FollowTagModel extends BaseModel<List<FollowTag>>
        implements UsingDaoModelType<FollowTag, FollowTagDao> {

    private FollowTagService service;
    private CurrentUser currentUser;
    private FollowTagDao dao;

    public FollowTagModel(Context context) {
        super(context);
        this.service = new FollowTagService(this.context);
        this.currentUser = CurrentUser.getInstance();
        this.dao = this.getDaoInstance();
    }

    @Override
    public FollowTagDao getDaoInstance() {
        return DaoFactory.getFollowTagDao();
    }

    @Override
    public List<FollowTag> load() {
        return this.dao.findAll();
    }

    @Override
    public void close() {
        this.dao.close();
    }

    @Override
    protected boolean isListView() {
        return true;
    }

    @Override
    protected void serviceRequest(final RequestType type) {
        String userId = this.currentUser.getID(this.context);

        this.service.request(this.page, userId, new OnRequestListener<List<FollowTag>>() {
            @Override
            public void onSuccess(List<FollowTag> results) {
                deliverSuccessAndProcessResults(type, results);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    @Override
    protected List<FollowTag> processResults(RequestType type, List<FollowTag> results) {
        List<FollowTag> realmTags = null;

        switch (type) {
            case FIRST:
            case REFRESH:
                this.dao.truncate();
                realmTags = this.dao.insert(results);
                break;
            case PAGING:
                realmTags = this.dao.insert(results);
                break;
        }

        return realmTags;
    }
}
