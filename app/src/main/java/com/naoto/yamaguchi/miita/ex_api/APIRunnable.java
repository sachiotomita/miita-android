package com.naoto.yamaguchi.miita.ex_api;

import android.content.Context;

import com.naoto.yamaguchi.miita.util.logger.Logger;

/**
 * Created by naoto on 16/06/25.
 */
public final class APIRunnable implements Runnable {

    private Context context;
    private APIListener listener;
    private APIHeader header;
    private Connection connection;
    private String urlString;
    private String method;
    private byte[] body;
    private Logger logger;

    // TODO: urlStringとmethod逆にする
    public APIRunnable(Context context,
                       String urlString,
                       String method,
                       byte[] body,
                       APIListener listener) {
        this.context = context;
        this.urlString = urlString;
        this.method = method;
        this.body = body;
        this.listener = listener;
        this.header = new APIHeader(this.context);
        this.connection = new Connection();
        this.logger = Logger.getInstance();
    }

    @Override
    public void run() {
        this.logger.info(this.urlString);

        try {
            this.connection.build(
                    this.urlString,
                    this.method,
                    this.header.getHeader(),
                    this.body
            );
        } catch (APIException e) {
            this.logger.error(e.toString());
            this.deliverError(e);
            return;
        }

        try {
            this.connection.request();
        } catch (APIException e) {
            this.logger.error(e.toString());
            this.deliverError(e);
            return;
        }

        Response response = this.connection.getResponse();

        if (response.getStatusCode() >= 400) {
            String message = "status code over 400";
            this.logger.error(message);
            this.deliverError(new APIException(message));
            return;
        }

        this.deliverSuccess(response);
    }

    private void deliverSuccess(Response response) {
        if (this.listener != null) {
            this.listener.onSuccess(response);
        }
    }

    private void deliverError(APIException e) {
        if (this.listener != null) {
            this.listener.onError(e);
        }
    }

}
