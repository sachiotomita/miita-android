package com.naoto.yamaguchi.miita.service;

import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Method;
import com.naoto.yamaguchi.miita.api.RequestType;
import com.naoto.yamaguchi.miita.entity.User;
import com.naoto.yamaguchi.miita.mapper.UserObjectMapper;

import java.util.Map;

/**
 * Request Authorize User.
 * GET: v2/authenticated_user
 *
 * Created by naoto on 16/07/01.
 */
public class AuthUserService implements RequestType<User> {

  public AuthUserService() {}

  @Override
  public Method getMethod() {
    return Method.GET;
  }

  @Override
  public String getPath() {
    return "/authenticated_user";
  }

  @Override
  public Map<String, String> getParameters() {
    return null;
  }

  @Override
  public User processResponse(String response) throws HttpException {
    try {
      return UserObjectMapper.map(response);
    } catch (HttpException e) {
      return null;
    }
  }
}
