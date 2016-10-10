package com.naoto.yamaguchi.miita.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by naoto on 2016/10/10.
 */

public final class Connection {

  private static final String CONTENT_TYPE_KEY = "Content-Type";
  private static final String APPLICATION_JSON_VALUE = "application/json";

  private HttpURLConnection connection;

  public Connection() {
    this.connection = null;
  }

  // TODO: throw e
  public void build(String urlString,
                    Method method,
                    RequestHeaders headers,
                    byte[] body) {
    try {
      URL url = new URL(urlString);
      this.connection = (HttpURLConnection)url.openConnection();

    } catch (IOException e) {
      // TODO:
    }
  }

  public int getStatusCode() throws IOException {
    try {
      return this.connection.getResponseCode();
    } catch (IOException e) {
      throw e;
    }
  }

  public Map<String, List<String>> getResponseHeaders() {
    return this.connection.getHeaderFields();
  }

  public String getRawBody() throws IOException {
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
      throw e;
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        throw e;
      }
    }
  }

  private void setMethod(Method method) {
    try {
      this.connection.setRequestMethod(method.toString());
    } catch (ProtocolException e) {
      // TODO:
    }
  }

  private void setHeaders(RequestHeaders headers) {
    Map<String, String> map = headers.toMap();
    for (Map.Entry<String, String> entry: map.entrySet()) {
      this.connection.setRequestProperty(entry.getKey(), entry.getValue());
    }
  }

  private void setBody(Method method, byte[] body) {
    if (method == Method.POST || method == Method.PUT) {
      if (body == null) return;

      try {
        this.connection.addRequestProperty(CONTENT_TYPE_KEY, APPLICATION_JSON_VALUE);
        this.connection.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(this.connection.getOutputStream());
        out.write(body);
        out.close();
      } catch (IOException e) {
        // TODO:
      }
    }
  }


}
