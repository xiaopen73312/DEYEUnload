package com.deye.demo.httpService;

import com.alibaba.fastjson.JSONObject;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SimpleHttpClient {

    @Autowired
    private RestTemplate restTemplate;

    public String simplePost(String requestJson, String url) throws Exception {

        HttpHeaders headers = getJsonHeaders();
//        ResponseEntity<String> responseEntity = post(requestJson, url, headers);
//        return responseEntity.getBody().toString();
        return postForJSONObject(requestJson, url, headers);

    }

    public String simpleGet(String url) throws Exception {

        restTemplate.getMessageConverters().set(1,
                new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        return responseEntity.getBody().toString();
    }

    public ResponseEntity<String> post(String requestJson, String url, HttpHeaders headers)
            throws Exception {

        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }

    public String postForJSONObject(String requestJson, String url, HttpHeaders headers) {

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(url,
                HttpMethod.POST, entity, JSONObject.class);
        return exchange.getBody().toString();
    }
    public ResponseEntity<String> exchange(String requestJson,
            String url,
            HttpHeaders headers,
            String method)
            throws Exception {

        HttpMethod httpMethod = HttpMethod.POST;
        if (method.equalsIgnoreCase("get")) {
            httpMethod = HttpMethod.GET;
        }
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        return restTemplate.exchange(url, httpMethod, entity, String.class);
    }

    public HttpHeaders getJsonHeaders() {

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        headers.add("Content-Type", "application/json");
        return headers;
    }

    public static void reInitMessageConverter(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }
        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(1, converter);
    }


}
