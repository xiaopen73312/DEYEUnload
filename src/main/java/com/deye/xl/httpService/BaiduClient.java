package com.deye.demo.httpService;

import org.springframework.stereotype.Component;

@Component
public class UserClient extends SimpleHttpClient {

    public String gettmpstr(String Latitude, String Longitude) throws Exception {
        String URL = "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&mode=1&x=" + Longitude
                + "&y=" + Latitude;

        String entityBody = simpleGet(URL);

        return entityBody;
    }

//
//    public String tett() throws Exception {
//        String url = "http://47.97.251.253:5000/mint/v1/device/Push/factory/login";
//        JSONObject gjson = new JSONObject();
//        gjson.put("account", "MintFactory");
//        gjson.put("password", "MintFactory123456");
//        String jsonstr = gjson.toJSONString();
//        try {
//            String responseEntity = this.simplePost(jsonstr, url);
//            JSONArray jsonArray = JSONArray.parseArray(responseEntity);
//            JSONObject tmpstrsonObject = jsonArray.getJSONObject(0);
//            String data = tmpstrsonObject.getString("data");
//            return data;
//        } catch (Exception e) {
//
//            return null;
//        }
//    }
}
