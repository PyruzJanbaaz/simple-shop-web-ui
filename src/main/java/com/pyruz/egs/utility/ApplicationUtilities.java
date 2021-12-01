package com.pyruz.egs.utility;


import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Component
public class ApplicationUtilities {
    private static ApplicationUtilities applicationUtilities;

    public static ApplicationUtilities getInstance() {
        if (applicationUtilities == null) {
            applicationUtilities = new ApplicationUtilities();
        }
        return applicationUtilities;
    }

    public HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        }
        return null;
    }

    public static boolean isExists(String o) {
        return o != null && !o.isEmpty() && !o.trim().equalsIgnoreCase("null");
    }
}
