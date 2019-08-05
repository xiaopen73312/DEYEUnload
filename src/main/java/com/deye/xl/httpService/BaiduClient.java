package com.deye.xl.httpService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BaiduClient extends SimpleHttpClient {

    private static final Logger log = LoggerFactory.getLogger(BaiduClient.class);

    public String gettmpstr(String Latitude, String Longitude) {
        try {
            String URL =
                    "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&mode=1&x=" + Longitude
                            + "&y=" + Latitude;

            String entityBody = simpleGet(URL);
            log.info("getUrl={}", URL);
            log.info("entityBody={}", entityBody);
            return entityBody;
        } catch (Exception e) {
            log.error("getUrl Exception={}", e);
            return "";
        }

    }

}
