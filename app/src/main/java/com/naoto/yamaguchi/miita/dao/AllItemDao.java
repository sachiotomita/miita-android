package com.naoto.yamaguchi.miita.dao;

import com.naoto.yamaguchi.miita.dao.base.BaseDao;
import com.naoto.yamaguchi.miita.entity.api.AllItem;

/**
 * Created by naoto on 16/06/29.
 */
public final class AllItemDao extends BaseDao<AllItem> {
    public AllItemDao() {
        super();
    }

    @Override
    protected Class<AllItem> getClazz() {
        return AllItem.class;
    }
}
