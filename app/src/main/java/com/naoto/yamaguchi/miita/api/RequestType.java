package com.naoto.yamaguchi.miita.api;

import java.util.Map;

/**
 * Created by naoto on 2016/10/08.
 */

public interface RequestType<T> {

  Method getMethod();

  String getPath();

  Map<String, String> getParameters();

  T processResponse(String response) throws HttpException;
}
