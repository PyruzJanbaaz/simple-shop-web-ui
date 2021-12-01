package com.pyruz.egs.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyruz.egs.exception.ServiceException;
import com.pyruz.egs.model.dto.base.BaseDTO;
import com.pyruz.egs.utility.ApplicationMessages;
import com.pyruz.egs.utility.ApplicationUtilities;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class RestTemplateException implements ResponseErrorHandler {

    private ApplicationMessages applicationMessages;

    public RestTemplateException(ApplicationMessages applicationMessages) {
        this.applicationMessages = applicationMessages;
    }

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) {
        return true;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (!clientHttpResponse.getStatusCode().is2xxSuccessful()) {
            String newLine = System.getProperty("line.separator");
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientHttpResponse.getBody(), "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            boolean flag = false;
            while ((line = reader.readLine()) != null) {
                result.append(flag ? newLine : "").append(line);
                flag = true;
            }
            String requestedWithHeader = ApplicationUtilities.getInstance().getCurrentHttpRequest().getHeader("X-Requested-With");
            if (requestedWithHeader == null) {
                throw new HttpClientErrorException(clientHttpResponse.getStatusCode(), getMetaMessage(result));
            } else {
                String errorMessage;
                if (clientHttpResponse.getStatusCode().equals(HttpStatus.BAD_GATEWAY)) {
                    errorMessage = applicationMessages.getProperty("application.message.bad.gateway.description");
                } else {
                    JSONObject jsonObject = new JSONObject(result.toString());
                    if (clientHttpResponse.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                        errorMessage = applicationMessages.getProperty("application.message.access.denied.description");
                    } else {
                        try {
                            errorMessage = jsonObject.getString("message");
                        } catch (Exception ex) {
                            errorMessage = jsonObject.getJSONObject("meta").get("message").toString();
                        }
                    }
                }
                throw ServiceException.builder()
                        .code(clientHttpResponse.getStatusCode().value())
                        .message(errorMessage)
                        .build();
            }
        }
    }

    private String getMetaMessage(StringBuilder result) {
        try {
            BaseDTO baseDTO = new ObjectMapper().readValue(result.toString(), BaseDTO.class);
            return baseDTO.getMeta().getMessage();
        } catch (IOException e) {
            return result.toString();
        }
    }
}
