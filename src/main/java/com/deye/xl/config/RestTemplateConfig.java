package com.deye.xl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 注入RestTemplate 并设置Timeout
 */
@Configuration
@Component
@EnableAutoConfiguration
public class RestTemplateConfig {

    @Value("${http.connectTimeout}")
    private String connectionTimeOut;

    @Value("${http.connectionRequestTimeout}")
    private String connectionRequestTimeout;
    @Value("${http.readTimeout}")
    private String readTimeOut;
    @Value("${httpClient.connection.pool.size}")
    private String poolMaxTotal;
    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient());
        factory.setConnectionRequestTimeout(Integer.parseInt(connectionRequestTimeout));
        factory.setConnectTimeout(Integer.parseInt(connectionTimeOut));
        factory.setReadTimeout(Integer.parseInt(readTimeOut));
        return factory;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate(httpRequestFactory());
        List<HttpMessageConverter<?>> messageConverters = template.getMessageConverters();
        messageConverters.add(new FormHttpMessageConverter());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);

        //不加可能会出现异常
        //Could not extract response: no suitable HttpMessageConverter found for response type [class ]
        //添加常见的MIMEType
        MediaType[] mediaTypes = new MediaType[]{
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM,

                MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN,
                MediaType.TEXT_XML,
                MediaType.APPLICATION_STREAM_JSON,
                MediaType.APPLICATION_ATOM_XML,
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_JSON_UTF8,
                MediaType.APPLICATION_PDF,
        };

        converter.setSupportedMediaTypes(Arrays.asList(mediaTypes));

        try {
            //通过反射设置MessageConverters
            Field field = template.getClass().getDeclaredField("messageConverters");

            field.setAccessible(true);

            List<HttpMessageConverter<?>> orgConverterList = (List<HttpMessageConverter<?>>) field
                    .get(template);

            Optional<HttpMessageConverter<?>> opConverter = orgConverterList.stream()
                    .filter(x -> x.getClass().getName()
                            .equalsIgnoreCase(MappingJackson2HttpMessageConverter.class
                                    .getName()))
                    .findFirst();

            if (opConverter.isPresent() == false) {
                return template;
            }

            messageConverters.add(converter);//添加MappingJackson2HttpMessageConverter

            //添加原有的剩余的HttpMessageConverter
            List<HttpMessageConverter<?>> leftConverters = orgConverterList.stream()
                    .filter(x -> x.getClass().getName()
                            .equalsIgnoreCase(MappingJackson2HttpMessageConverter.class
                                    .getName()) == false)
                    .collect(Collectors.toList());

            messageConverters.addAll(leftConverters);

            System.out.println(String.format("【HttpMessageConverter】原有数量：%s，重新构造后数量：%s"
                    , orgConverterList.size(), messageConverters.size()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        template.setMessageConverters(messageConverters);
        return template;
    }

    @Bean
    public HttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(Integer.parseInt(poolMaxTotal));
        return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
    }

}