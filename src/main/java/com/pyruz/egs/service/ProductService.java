package com.pyruz.egs.service;

import com.pyruz.egs.model.domain.UpdateMessageBean;
import com.pyruz.egs.model.domain.category.CategoryNewRequest;
import com.pyruz.egs.model.domain.category.CategoryUpdateRequest;
import com.pyruz.egs.model.domain.product.ProductCommentRequest;
import com.pyruz.egs.model.domain.product.ProductNewRequest;
import com.pyruz.egs.model.domain.product.ProductRateRequest;
import com.pyruz.egs.model.domain.product.ProductUpdateRequest;
import com.pyruz.egs.model.dto.base.BaseDTO;
import com.pyruz.egs.utility.ApplicationProperties;
import com.pyruz.egs.utility.RestTemplateUtility;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;

@Service
public class ProductService {

    final RestTemplateUtility restTemplateUtility;
    final ApplicationProperties applicationProperties;

    public ProductService(RestTemplateUtility restTemplateUtility, ApplicationProperties applicationProperties) {
        this.restTemplateUtility = restTemplateUtility;
        this.applicationProperties = applicationProperties;
    }

    public BaseDTO getAll() {
        return restTemplateUtility.sendRequest(
                applicationProperties.getProperty("base.url") + applicationProperties.getProperty("url.product.find.all"),
                HttpMethod.GET,
                null
        );
    }

    public BaseDTO searchProduct(String title, Float minPrice, Float maxPrice, Short minRate, Short maxRate) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                applicationProperties.getProperty("base.url") + applicationProperties.getProperty("url.product.search")
        );
        builder.queryParam("title", title);
        builder.queryParam("minPrice", minPrice);
        builder.queryParam("maxPrice", maxPrice);
        builder.queryParam("minRate", minRate);
        builder.queryParam("maxRate", maxRate);
        return restTemplateUtility.sendRequestUrlencoded(builder, HttpMethod.GET);
    }

    public BaseDTO getById(Integer id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                applicationProperties.getProperty("base.url") +
                        applicationProperties.getProperty("url.product")
        );
        builder.queryParam("id", id);
        return restTemplateUtility.sendRequestUrlencoded(builder, HttpMethod.GET);
    }

    public BaseDTO getAllByCategoryId(Integer categoryId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                applicationProperties.getProperty("base.url")
                        + applicationProperties.getProperty("url.product.find.cat.id"));
        builder.queryParam("categoryId", categoryId);

        return restTemplateUtility.sendRequestUrlencoded(builder, HttpMethod.GET);
    }

    public BaseDTO newProduct(ProductNewRequest productNewRequest) {
        return restTemplateUtility.sendRequest(
                applicationProperties.getProperty("base.url") + applicationProperties.getProperty("url.product"),
                HttpMethod.POST,
                productNewRequest
        );
    }

    public BaseDTO updateProduct(ProductUpdateRequest productUpdateRequest) {
        return restTemplateUtility.sendRequest(
                applicationProperties.getProperty("base.url") + applicationProperties.getProperty("url.product"),
                HttpMethod.PUT,
                productUpdateRequest
        );
    }

    public BaseDTO rateProduct(ProductRateRequest productRateRequest) {
        return restTemplateUtility.sendRequest(
                applicationProperties.getProperty("base.url") + applicationProperties.getProperty("url.product.rate"),
                HttpMethod.PUT,
                productRateRequest
        );
    }

    public BaseDTO commentProduct(ProductCommentRequest productCommentRequest) {
        return restTemplateUtility.sendRequest(
                applicationProperties.getProperty("base.url") + applicationProperties.getProperty("url.product.comment"),
                HttpMethod.PUT,
                productCommentRequest
        );
    }

    public BaseDTO removeProduct(Integer id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
                applicationProperties.getProperty("base.url") +
                        applicationProperties.getProperty("url.product")
        );
        builder.queryParam("id", id);
        return restTemplateUtility.sendRequestUrlencoded(builder, HttpMethod.DELETE);
    }

}
