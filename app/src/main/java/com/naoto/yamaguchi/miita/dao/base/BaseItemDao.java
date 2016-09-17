package com.naoto.yamaguchi.miita.dao.base;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by naoto on 16/09/17.
 */
public abstract class BaseItemDao<T extends RealmObject>
        implements BaseDao<List<T>, RealmResults<T>> {

    protected Realm realm;
    protected Class<T> aClass;

    public BaseItemDao(Class<T> aClass) {
        this.aClass = aClass;
        this.setClient();
    }

    @Override
    public void setClient() {
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public List<T> insert(List<T> items) {
        this.realm.beginTransaction();
        List<T> realmItems = this.realm.copyToRealmOrUpdate(items);
        this.realm.commitTransaction();
        return realmItems;
    }

    @Override
    public RealmResults<T> findAll() {
        return this.realm
                .where(this.aClass)
                .findAll();
    }

    @Override
    public void truncate() {
        RealmResults<T> items = this.findAll();
        this.realm.beginTransaction();
        items.deleteAllFromRealm();
        this.realm.commitTransaction();
    }

    @Override
    public void close() {
        this.realm.close();
    }
}
