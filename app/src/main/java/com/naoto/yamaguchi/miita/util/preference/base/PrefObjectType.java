package com.naoto.yamaguchi.miita.util.preference.base;

/**
 * Created by naoto on 2016/09/25.
 */

public interface PrefObjectType<T> {
    public T get();
    public void set(T value);
    public void delete();
}
