package com.naoto.yamaguchi.miita.dao.base;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by naoto on 2016/09/23.
 */

public abstract class BaseDao<T extends RealmObject> {

    protected Realm realm;
    protected Class<T> aClass;

    protected abstract Class<T> getClazz();

    public BaseDao() {
        this.realm = Realm.getDefaultInstance();
        this.aClass = this.getClazz();
    }

    public List<T> insert(List<T> objects) {
        this.realm.beginTransaction();
        List<T> realmObjects = this.realm.copyToRealmOrUpdate(objects);
        this.realm.commitTransaction();
        return realmObjects;
    }

    public RealmResults<T> findAll() {
        return this.realm
                .where(this.aClass)
                .findAll();
    }

    public void truncate() {
        RealmResults<T> objects = this.findAll();
        this.realm.beginTransaction();
        objects.deleteAllFromRealm();
        this.realm.commitTransaction();
    }

    public void close() {
        this.realm.close();
    }
}
