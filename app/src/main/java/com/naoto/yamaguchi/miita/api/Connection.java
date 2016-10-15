package com.naoto.yamaguchi.miita.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HTTP Connection class.
 *
 * Created by naoto on 2016/10/10.
 */

final class Connection {

  private static final String CONTENT_TYPE_KEY = "Content-Type";
  private static final String APPLICATION_JSON_VALUE = "application/json";

  private HttpURLConnection connection;

  public Connection() {
    this.connection = null;
  }

  public void build(String urlString,
                    Method method,
                    RequestHeaders headers,
                    byte[] body) throws HttpException {
    try {
      URL url = new URL(urlString);
      this.connection = (HttpURLConnection)url.openConnection();
      this.setMethod(method);
      this.setHeaders(headers);
      this.setBody(method, body);
    } catch (IOException e) {
      throw new HttpException(e.toString());
    }
  }

  public void connect() throws HttpException {
    try {
      this.connection.connect();
    } catch (IOException e) {
      throw new HttpException(e.toString());
    }
  }

  public void disConnect() {
    if (this.connection != null) {
      this.connection.disconnect();
    }
  }

  public int getStatusCode() throws HttpException {
    try {
      return this.connection.getResponseCode();
    } catch (IOException e) {
      throw new HttpException(e.toString());
    }
  }

  public Map<String, String> getResponseHeaders() {
    final Map<String, List<String>> headers = this.connection.getHeaderFields();
    Map<String, String> returnHeaders = new HashMap<>();

    for (String key: headers.keySet()) {
      final List<String> valueList = headers.get(key);
      final StringBuilder values = new StringBuilder();
      for (String value: valueList) {
        values.append(value + " ");
      }
      returnHeaders.put(key, values.toString());
    }

    return returnHeaders;
  }

  public String getRawBody() throws HttpException {
    StringBuffer body = new StringBuffer();
    BufferedReader reader = null;

    try {
      InputStream stream = this.connection.getInputStream();
      InputStreamReader inputStreamReader = new InputStreamReader(stream);
      reader = new BufferedReader(inputStreamReader);

      String line = null;
      while ((line = reader.readLine()) != null) {
        body.append(line);
      }

      return body.toString();
    } catch (IOException e) {
      throw new HttpException(e.toString());
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        throw new HttpException(e.toString());
      }
    }
  }

  private void setMethod(Method method) throws HttpException {
    try {
      this.connection.setRequestMethod(method.toString());
    } catch (ProtocolException e) {
      throw new HttpException(e.toString());
    }
  }

  private void setHeaders(RequestHeaders headers) {
    Map<String, String> map = headers.toMap();
    for (Map.Entry<String, String> entry: map.entrySet()) {
      this.connection.setRequestProperty(entry.getKey(), entry.getValue());
    }
  }

  private void setBody(Method method, byte[] body) throws HttpException {
    if (method == Method.POST || method == Method.PUT) {
      if (body == null) return;

      try {
        this.connection.addRequestProperty(CONTENT_TYPE_KEY, APPLICATION_JSON_VALUE);
        this.connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(this.connection.getOutputStream());
        out.write(body);
        out.close();
      } catch (IOException e) {
        throw new HttpException(e.toString());
      }
    }
  }
}
