package com.myretail.productdemo.exceptions;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@NoArgsConstructor
public class ExternalServiceExceptionHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        final HttpStatus statusCode = httpResponse.getStatusCode();
        return statusCode.series() == HttpStatus.Series.CLIENT_ERROR
                || statusCode.series() == HttpStatus.Series.SERVER_ERROR;
    }

    @SneakyThrows
    @Override
    public void handleError(ClientHttpResponse httpResp) {
        final HttpStatus statusCode = httpResp.getStatusCode();

        String messageBody = "RESPONSE HAS NO BODY";
        if(httpResp.getBody() != null) {

            StringBuilder inputStringBuilder = new StringBuilder();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResp.getBody(), "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            messageBody = inputStringBuilder.toString();
        }
        if (statusCode.is4xxClientError() ) {
                throw new ResourceNotFoundException(messageBody);
        } else {

            throw new ExternalServiceRuntimeException(messageBody);
        }
    }

}