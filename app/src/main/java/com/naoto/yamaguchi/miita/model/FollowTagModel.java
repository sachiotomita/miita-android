package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.FollowTagDao;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.model.base.BaseObjectListModel;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.FollowTagService;
import com.naoto.yamaguchi.miita.util.RequestType;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

import java.util.List;

/**
 * Created by naoto on 16/07/12.
 */
public final class FollowTagModel extends BaseObjectListModel<FollowTag> {

    private FollowTagService service;
    private FollowTagDao dao;
    private CurrentUser currentUser;

    public FollowTagModel(Context context) {
        super(context);
        this.service = new FollowTagService(this.context);
        this.dao = new FollowTagDao();
        this.currentUser = CurrentUser.getInstance();
    }

    @Override
    protected void serviceRequest(final RequestType type) {
        String userId = this.currentUser.getID(this.context);

        this.service.request(this.page, userId, new FollowTagService.OnRequestListener() {
            @Override
            public void onSuccess(List<FollowTag> results) {
                deliverSuccess(type, results);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    @Override
    protected List<FollowTag> load() {
        return this.dao.findAll();
    }

    @Override
    protected void close() {
        this.dao.close();
    }

    @Override
    protected void deliverSuccess(final RequestType type, final List<FollowTag> results) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                isPaging = false;
                List<FollowTag> tags = null;

                switch (type) {
                    case FIRST:
                    case REFRESH:
                        dao.truncate();
                        tags = dao.insert(results);
                        break;
                    case PAGING:
                        tags = dao.insert(results);
                        break;
                }

                listener.onSuccess(results);
                listener.onComplete();
            }
        });
    }

    @Override
    protected void deliverError(final APIException e) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                isPaging = false;
                listener.onError(e);
                listener.onComplete();
            }
        });
    }
}
