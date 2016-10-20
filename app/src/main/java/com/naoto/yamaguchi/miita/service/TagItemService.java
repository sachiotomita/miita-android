package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Method;
import com.naoto.yamaguchi.miita.api.RequestType;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.mapper.ItemListObjectMapper;
import com.naoto.yamaguchi.miita.util.preference.PerPage;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request Tag ID Item.
 * GET: v2/tags/__tag_id__/items
 *
 * Created by naoto on 16/07/17.
 */
public final class TagItemService implements RequestType<List<Item>> {

  private final Context context;
  private String tagId;
  private int page;

  public TagItemService(Context context) {
    this.context = context;
  }

  public TagItemService setTagId(String tagId) {
    this.tagId = tagId;
    return this;
  }

  public TagItemService setPage(int page) {
    this.page = page;
    return this;
  }

  @Override
  public Method getMethod() {
    return Method.GET;
  }

  @Override
  public String getPath() {
    return "/tags/" + this.tagId + "/items";
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
  public List<Item> processResponse(String response) throws HttpException {
    try {
      return ItemListObjectMapper.map(response, Item.class);
    } catch (JSONException | IllegalAccessException | InstantiationException e) {
      return null;
    }
  }
}
