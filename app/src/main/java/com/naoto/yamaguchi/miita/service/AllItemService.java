package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Method;
import com.naoto.yamaguchi.miita.api.RequestType;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.mapper.ItemListObjectMapper;
import com.naoto.yamaguchi.miita.util.preference.PerPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Get All Item.
 * request: v2/items/
 *
 * Created by naoto on 16/06/29.
 */
public final class AllItemService implements RequestType<List<AllItem>> {

  private final Context context;
  private int page;

  public AllItemService(Context context) {
    this.context = context;
    this.page = 1;
  }

  public AllItemService setPage(int page) {
    this.page = page;
    return this;
  }

  @Override
  public Method getMethod() {
    return Method.GET;
  }

  @Override
  public String getPath() {
    return "/items";
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
  public List<AllItem> processResponse(String response) throws HttpException {
    try {
      return ItemListObjectMapper.map(response, AllItem.class);
    } catch (HttpException e) {
      return null;
    }
  }
}
