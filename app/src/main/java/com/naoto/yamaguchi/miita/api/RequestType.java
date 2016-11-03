package com.naoto.yamaguchi.miita.api;

import java.util.Map;

/**
 * Request Interface.
 *
 * Created by naoto on 2016/10/08.
 */

public interface RequestType<T> {

  /**
   * HTTP Method.
   * @return Method
   */
  Method getMethod();

  /**
   * request path.
   * @return String
   */
  String getPath();

  /**
   * query params or request body.
   * @return Map<String, String>
   */
  Map<String, String> getParameters();

  /**
   * @param response
   * @return null or process response.
   * @throws HttpException
   */
  T processResponse(String response) throws HttpException;
}
