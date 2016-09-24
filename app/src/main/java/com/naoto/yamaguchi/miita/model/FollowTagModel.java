package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.dao.FollowTagDao;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.model.base.BaseRealmObjectListModel;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.FollowTagService;
import com.naoto.yamaguchi.miita.model.base.RequestType;

import java.util.List;

/**
 * Created by naoto on 16/07/12.
 */
public final class FollowTagModel extends BaseRealmObjectListModel<FollowTag, FollowTagDao> {

    private FollowTagService service;
    private CurrentUser currentUser;

    public FollowTagModel(Context context) {
        super(context);
        this.service = new FollowTagService(this.context);
        this.currentUser = CurrentUser.getInstance();
    }

    @Override
    protected FollowTagDao getDaoInstance() {
        return new FollowTagDao();
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
}
