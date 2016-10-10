package com.naoto.yamaguchi.miita.api;

/**
 * HTTP method.
 *
 * Created by naoto on 2016/10/10.
 */

public enum Method {
  GET {
    @Override
    public String toString() {
      return "GET";
    }
  },
  POST {
    @Override
    public String toString() {
      return "POST";
    }
  },
  PUT {
    @Override
    public String toString() {
      return "PUT";
    }
  },
  DELETE {
    @Override
    public String toString() {
      return "DELETE";
    }
  };

  public abstract String toString();
}
