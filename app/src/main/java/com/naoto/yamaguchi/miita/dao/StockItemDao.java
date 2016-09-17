package com.naoto.yamaguchi.miita.dao;

import com.naoto.yamaguchi.miita.dao.base.BaseItemDao;
import com.naoto.yamaguchi.miita.entity.StockItem;

/**
 * Created by naoto on 16/06/30.
 */
public final class StockItemDao extends BaseItemDao<StockItem> {
    public StockItemDao() {
        super(StockItem.class);
    }
}
