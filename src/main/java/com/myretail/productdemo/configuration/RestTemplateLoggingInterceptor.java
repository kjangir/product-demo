package com.myretail.productdemo.configuration;

import com.google.common.io.ByteStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateLoggingInterceptor.class);

    private boolean isEnabled;

    public RestTemplateLoggingInterceptor(final boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        if (!isEnabled)
            return execution.execute(request, body);
        traceRequest(request, body);

        final ClientHttpResponse response = execution.execute(request, body);

        traceResponse(request, response);

        return response;
    }

    private void traceRequest(final HttpRequest request, final byte[] body) {

        try {
            String requestLog = String.format("REQUEST:[%1s] [%2s] Body: %3s",
                    request.getMethod(),
                    request.getURI(),
                    (body == null || body.length == 0) ? "" : new String(body, "UTF-8"));

            LOGGER.info(requestLog);
        } catch (Exception e) {
            LOGGER.warn("Failed to log Request for {} request to {}", request.getMethod(), request.getURI(), e);
        }
    }
    private void traceResponse(final HttpRequest request, final ClientHttpResponse response) {
        try {

            String responseLog = String.format("RESPONSE:[%1s] [%2s] STATUS: [%3s]",
                    request.getMethod(),
                    request.getURI(),
                    response.getStatusCode());

            LOGGER.info(responseLog);
        } catch (Exception e) {
            LOGGER.warn(e.getMessage(), response);
        }
    }
}
