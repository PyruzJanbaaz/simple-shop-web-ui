package com.pyruz.egs.service;

import com.pyruz.egs.model.domain.category.CategoryNewRequest;
import com.pyruz.egs.model.domain.category.CategoryUpdateRequest;
import com.pyruz.egs.model.dto.base.BaseDTO;
import com.pyruz.egs.utility.ApplicationProperties;
import com.pyruz.egs.utility.RestTemplateUtility;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CategoryService {

    final RestTemplateUtility restTemplateUtility;
    final ApplicationProperties applicationProperties;

    public CategoryService(RestTemplateUtility restTemplateUtility, ApplicationProperties applicationProperties) {
        this.restTemplateUtility = restTemplateUtility;
        this.applicationProperties = applicationProperties;
    }

    public BaseDTO getAll() {
        return restTemplateUtility.sendRequest(
                applicationProperties.getProperty("base.url") + applicationProperties.getProperty("url.category"),
                HttpMethod.GET,
                null
        );
    }

    public BaseDTO newCategory(CategoryNewRequest categoryNewRequest) {
        return restTemplateUtility.sendRequest(
                applicationProperties.getProperty("base.url") + applicationProperties.getProperty("url.category"),
                HttpMethod.POST,
                categoryNewRequest
        );
    }

    public BaseDTO updateCategory(CategoryUpdateRequest categoryUpdateRequest) {
        return restTemplateUtility.sendRequest(
                applicationProperties.getProperty("base.url") + applicationProperties.getProperty("url.category"),
                HttpMethod.PUT,
                categoryUpdateRequest
        );
    }

    public BaseDTO removeCategory(Integer id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                applicationProperties.getProperty("base.url") + applicationProperties.getProperty("url.category"));
        builder.queryParam("id", id);
        return restTemplateUtility.sendRequestUrlencoded(builder, HttpMethod.DELETE);
    }

}
