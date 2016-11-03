package com.naoto.yamaguchi.miita.service;

import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Method;
import com.naoto.yamaguchi.miita.api.RequestType;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.mapper.ItemListObjectMapper;
import com.naoto.yamaguchi.miita.util.preference.PerPage;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request Stock Item.
 * GET: v2/users/__user_id_/stocks
 *
 * Created by naoto on 16/06/30.
 */
public final class StockItemService implements RequestType<List<StockItem>> {

  private String userId;
  private int page;

  public StockItemService() {
    this.page = 1;
  }

  public StockItemService setUserId(String userId) {
    this.userId = userId;
    return this;
  }

  public StockItemService setPage(int page) {
    this.page = page;
    return this;
  }

  @Override
  public Method getMethod() {
    return Method.GET;
  }

  @Override
  public String getPath() {
    return "/users/" + this.userId + "/stocks";
  }

  @Override
  public Map<String, String> getParameters() {
    return new HashMap<String, String>() {
      {
        put("page", Integer.toString(page));
        put("per_page", PerPage.get());
      }
    };
  }

  @Override
  public List<StockItem> processResponse(String response) throws HttpException {
    try {
      return ItemListObjectMapper.map(response, StockItem.class);
    } catch (JSONException | IllegalAccessException | InstantiationException e) {
      return null;
    }
  }
}
