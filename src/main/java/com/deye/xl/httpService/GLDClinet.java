package com.deye.xl.httpService;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 通用接口 广联达
 */
@Component

@PropertySource(value = "CustomerConfig.properties")
public class GLDClinet {

    private static final Logger log = LoggerFactory.getLogger(GLDClinet.class);
    @Autowired
    private RestTemplate restTemplate;

    @Value("${gld.base.url}")
    private String baseUrl;//广联达


    public String postCustomerAPI(String requestJson, String apiUrl, String BearerToke)
            throws Exception {

        final String url = baseUrl + apiUrl;
        log.info("postCustomerAPI url={}", url);
        log.info("postCustomerAPI requestJson={}", requestJson);
        String result = postForJSONObject(requestJson, url, BearerToke);
        log.info("postCustomerAPI result={}", result);
        return result;
    }


    public String postForJSONObject(String requestJson, String url, String BearerToke) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Authorization",
                BearerToke);
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(url,
                HttpMethod.POST, entity, JSONObject.class);
        return exchange.getBody().toString();
    }

}
