package com.naoto.yamaguchi.miita.ex_api;

import com.naoto.yamaguchi.miita.util.logger.Logger;

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
 * Created by naoto on 16/06/23.
 */
public final class Connection {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    private HttpURLConnection connection;
    private Response response;
    private Logger logger;

    public Connection() {
        this.connection = null;
        this.response = new Response();
        this.logger = Logger.getInstance();
    }

    public void build(String urlString,
                      String method,
                      Map<String, String> header,
                      byte[] body) throws APIException {
        try {
            URL url = new URL(urlString);
            this.connection = (HttpURLConnection)url.openConnection();
            this.setMethod(method);
            this.setHeader(header);
            this.setBody(method, body);
        } catch (IOException e) {
            throw new APIException(e.toString());
        }
    }

    public void request() throws APIException {
        try {
            this.connection.connect();
        } catch (IOException e) {
            this.logger.error(e.toString());
            throw new APIException(e.toString());
        }
    }

    public void disConnect() {
        if (this.connection != null) {
            this.connection.disconnect();
        }
    }

    // FIXME: 受け取る形にする？
    public Response getResponse() {
        this.getStatusCode();
        this.getResponseHeader();
        this.getResponseBody();
        return this.response;
    }

    private void setMethod(String method) {
        try {
            this.connection.setRequestMethod(method);
        } catch (ProtocolException e) {
            this.logger.error("error set request method");
            this.logger.error(e.toString());
        }
    }

    private void setHeader(Map<String, String> header) {
        for (Map.Entry<String, String> entry: header.entrySet()) {
            this.connection.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private void setBody(String method, byte[] body) throws IOException {
        if (method.equals("POST") && body != null) {
            this.connection.addRequestProperty(CONTENT_TYPE, APPLICATION_JSON);
            this.connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(this.connection.getOutputStream());
            out.write(body);
            out.close();
        }
    }

    private void getStatusCode() {
        try {
            int statusCode = this.connection.getResponseCode();
            this.response.setStatusCode(statusCode);
        } catch (IOException e) {
            this.logger.error("error get status code");
            this.logger.error(e.toString());
        }
    }

    private void getResponseHeader() {
        Map<String, List<String>> header = this.connection.getHeaderFields();

        for (Map.Entry<String, List<String>> entry: header.entrySet()) {
            String value = this.connection.getHeaderField(entry.getKey());
            this.response.setHeader(entry.getKey(), value);
        }
    }

    private void getResponseBody() {
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

            this.response.setBody(body.toString());

        } catch (IOException e) {
            this.logger.error("error get body");
            this.logger.error(e.toString());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                this.logger.error(e.toString());
            }
        }
    }

}
