package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Method;
import com.naoto.yamaguchi.miita.api.RequestType;
import com.naoto.yamaguchi.miita.entity.FollowTag;
import com.naoto.yamaguchi.miita.mapper.TagListObjectMapper;
import com.naoto.yamaguchi.miita.util.preference.PerPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by naoto on 16/07/12.
 */
public final class FollowTagService implements RequestType<List<FollowTag>> {

  private final Context context;
  private String userId;
  private int page;

  public FollowTagService(Context context) {
    this.context = context;
    this.page = 1;
  }

  public FollowTagService setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public FollowTagService setPage(int page) {
    this.page = page;
    return this;
  }

  @Override
  public Method getMethod() {
    return Method.GET;
  }

  @Override
  public String getPath() {
    return "/users/" + this.userId + "/following_tags";
  }

  @Override
  public Map<String, String> getParameters() {
    return new HashMap<String, String>() {
      {
        put("page", Integer.toString(page));
        put("per_page", PerPage.get(context));
      }
    };
  }

  @Override
  public List<FollowTag> processResponse(String response) throws HttpException {
    try {
      return TagListObjectMapper.map(FollowTag.class, response);
    } catch (HttpException e) {
      return null;
    }
  }
}
