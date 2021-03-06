package com.deye.xl.httpService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 虎匠
 */
@Component

@PropertySource(value = "CustomerConfig.properties")
public class SZHJClinet {

    private static final Logger log = LoggerFactory.getLogger(SZHJClinet.class);
    @Autowired
    private SimpleHttpClient simpleHttpClient;

    @Value("${hj.base.url}")
    private String baseUrl;//
    @Value("${deye.appid}")
    private String appid;
    @Value("${deye.appsecret}")
    private String appsecret;

    public String postCustomerAPI(String requestJson, String apiUrl)
            throws Exception {

        final String url = baseUrl + apiUrl + "?appid=" + appid + "&appsecret=" + appsecret;
        log.info("postCustomerAPI url={}", url);
        log.info("postCustomerAPI requestJson={}", requestJson);
        String result = simpleHttpClient.simplePost(requestJson, url);
        log.info("postCustomerAPI result={}", result);
        return result;
    }

}
