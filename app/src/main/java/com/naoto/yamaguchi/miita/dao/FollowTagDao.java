package com.naoto.yamaguchi.miita.dao;

import com.naoto.yamaguchi.miita.dao.base.BaseTagDao;
import com.naoto.yamaguchi.miita.entity.FollowTag;

/**
 * Created by naoto on 16/07/12.
 */
public final class FollowTagDao extends BaseTagDao<FollowTag> {
    public FollowTagDao() {
        super(FollowTag.class);
    }
}
