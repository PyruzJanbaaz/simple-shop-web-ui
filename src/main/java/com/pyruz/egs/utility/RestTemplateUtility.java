package com.pyruz.egs.utility;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pyruz.egs.exception.ServiceException;
import com.pyruz.egs.model.dto.base.BaseDTO;
import com.pyruz.egs.model.dto.base.MetaDTO;
import com.pyruz.egs.model.dto.base.ResultDTO;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

@Component
public class RestTemplateUtility {

    final RestTemplate restTemplate;

    public RestTemplateUtility(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public BaseDTO sendRequest(String url, HttpMethod httpMethod, Object body) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<?> httpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
        return mapper(responseEntity);
    }

    public BaseDTO sendRequestUrlencoded(MultiValueMap<String, Object> multiValueMap, String url, HttpMethod httpMethod) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multiValueMap, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, httpEntity, String.class);
        return mapper(responseEntity);
    }

    public BaseDTO sendRequestUrlencoded(UriComponentsBuilder builder, HttpMethod httpMethod) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON.toString());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                httpMethod,
                entity,
                String.class);
        return mapper(responseEntity);
    }

    private BaseDTO mapper(ResponseEntity<String> responseEntity) {
        String res = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        BaseDTO baseDTO = new BaseDTO();
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            try {
                baseDTO = objectMapper.readValue(res, BaseDTO.class);
            } catch (Exception e) {
                try {
                    ResultDTO resultDTO = objectMapper.readValue(res, ResultDTO.class);
                    baseDTO.setMeta(
                            MetaDTO.builder()
                                    .code(resultDTO.getCode())
                                    .message(resultDTO.getMessage())
                                    .build()
                    );
                    baseDTO.setData(resultDTO.getData());
                    baseDTO.setTotal(resultDTO.getTotal());
                } catch (Exception ex) {
                    baseDTO.setMeta(MetaDTO.builder().build());
                    baseDTO.setData(res);
                }
            }
            return baseDTO;
        } else {
            throw ServiceException.builder()
                    .code(Integer.parseInt(res.split(" ")[0]))
                    .message(res)
                    .build();
        }
    }
}
