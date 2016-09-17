package com.naoto.yamaguchi.miita.dao;

import com.naoto.yamaguchi.miita.dao.base.BaseItemDao;
import com.naoto.yamaguchi.miita.entity.AllItem;

/**
 * Created by naoto on 16/06/29.
 */
public final class AllItemDao extends BaseItemDao<AllItem> {
    public AllItemDao() {
        super(AllItem.class);
    }
}
