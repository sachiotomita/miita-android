package com.naoto.yamaguchi.miita.dao;

import com.naoto.yamaguchi.miita.dao.base.BaseDao;
import com.naoto.yamaguchi.miita.entity.api.FollowTag;

/**
 * Created by naoto on 16/07/12.
 */
public final class FollowTagDao extends BaseDao<FollowTag> {
    public FollowTagDao() {
        super();
    }

    @Override
    protected Class<FollowTag> getClazz() {
        return FollowTag.class;
    }
}
