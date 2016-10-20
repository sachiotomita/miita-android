package com.naoto.yamaguchi.miita.model.base;

import com.naoto.yamaguchi.miita.util.exception.MiitaException;

/**
 * Model CallBack Listener.
 *
 * Created by naoto on 16/09/22.
 */
public interface OnModelListener<T> {

  void onSuccess(T results);

  void onError(MiitaException e);

  void onComplete();
}
