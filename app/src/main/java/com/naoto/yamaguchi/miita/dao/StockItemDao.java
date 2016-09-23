package com.naoto.yamaguchi.miita.dao;

import com.naoto.yamaguchi.miita.dao.base.BaseDao;
import com.naoto.yamaguchi.miita.entity.StockItem;

/**
 * Created by naoto on 16/06/30.
 */
public final class StockItemDao extends BaseDao<StockItem> {
    public StockItemDao() {
        super();
    }

    @Override
    protected Class<StockItem> getClazz() {
        return StockItem.class;
    }
}
