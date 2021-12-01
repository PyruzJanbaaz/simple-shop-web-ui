package com.pyruz.egs.utility;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
@PropertySources(
        {
                @PropertySource(name = "application", value = "classpath:/application.properties", encoding = "UTF-8", ignoreResourceNotFound = true),
                @PropertySource(name = "dev", value = "classpath:/application-dev.properties", encoding = "UTF-8", ignoreResourceNotFound = true)
        }
)
public class ApplicationProperties {
    @Resource
    private Environment environment;

    public String getProperty(String name) {
        try {
            return environment.getProperty(name).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return name;
        }
    }
}
