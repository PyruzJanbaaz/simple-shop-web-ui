package com.pyruz.egs.service;

import com.pyruz.egs.model.domain.UpdateMessageBean;
import com.pyruz.egs.model.dto.base.BaseDTO;
import com.pyruz.egs.utility.ApplicationProperties;
import com.pyruz.egs.utility.RestTemplateUtility;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;

@Service
public class SampleService {

    final RestTemplateUtility restTemplateUtility;
    final ApplicationProperties applicationProperties;

    public SampleService(RestTemplateUtility restTemplateUtility, ApplicationProperties applicationProperties) {
        this.restTemplateUtility = restTemplateUtility;
        this.applicationProperties = applicationProperties;
    }


    public BaseDTO startup(Long updateBaseDataTime) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(applicationProperties.getProperty("base.url") + "/v1/aa/startup");
        builder.queryParam("appVer", 0);
        builder.queryParam("language", LocaleContextHolder.getLocale().getLanguage());
        builder.queryParam("os", "WEB");
        builder.queryParam("osVer", 0);
        builder.queryParam("readMessageTime", new Date().getTime());
        builder.queryParam("updateBaseDataTime", updateBaseDataTime);
        return restTemplateUtility.sendRequestUrlencoded(builder, HttpMethod.GET);
    }

    public BaseDTO updateMessage(UpdateMessageBean updateMessageBean) {
        return restTemplateUtility.sendRequest(
                applicationProperties.getProperty("base.url") + "/v1/aa/message",
                HttpMethod.PUT,
                updateMessageBean
        );
    }

    public BaseDTO getAvailableExchanges() {
        return restTemplateUtility.sendRequest(
                applicationProperties.getProperty("base.url") + "/v1/aa/exchange",
                HttpMethod.GET,
                null
        );
    }

}
