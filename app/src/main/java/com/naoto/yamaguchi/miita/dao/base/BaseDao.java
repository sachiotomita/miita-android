package com.naoto.yamaguchi.miita.dao.base;

/**
 * Created by naoto on 16/09/17.
 */
public interface BaseDao<I, F> {
    void setClient();
    I insert(I items);
    F findAll();
    void truncate();
    void close();
}
