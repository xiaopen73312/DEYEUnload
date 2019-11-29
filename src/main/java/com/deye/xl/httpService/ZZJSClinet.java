package com.deye.xl.httpService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 通用接口 深圳智造建设
 */
@Component

@PropertySource(value = "CustomerConfig.properties")
public class ZZJSClinet {

    private static final Logger log = LoggerFactory.getLogger(ZZJSClinet.class);
    @Autowired
    private SimpleHttpClient simpleHttpClient;

    @Value("${szzz.base.url}")
    private String baseUrl;//


    public String postCustomerAPI(String requestJson, String apiUrl)
            throws Exception {

        final String url = baseUrl + apiUrl;
        log.info("postCustomerAPI url={}", url);
        log.info("postCustomerAPI requestJson={}", requestJson);
        String result = simpleHttpClient.simplePost(requestJson, url);
        log.info("postCustomerAPI result={}", result);
        return result;
    }

}
