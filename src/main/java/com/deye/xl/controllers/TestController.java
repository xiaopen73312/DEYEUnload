package com.deye.demo.controllers;

import com.deye.demo.constants.URIEndpoints;
import com.deye.demo.httpService.ChenDuClinet;
import com.deye.demo.httpService.YSClinet;
import com.deye.demo.model.ServiceResponse;
import com.deye.demo.postBody.ChenDuPost;
import com.deye.demo.postBody.HaiNanPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = URIEndpoints.TEST)
public class TestController {

    @Autowired
    private ChenDuClinet towerCraneClinet;

    @Autowired
    private ChenDuPost chenDuPost;
    @Autowired
    HaiNanPost haiNanPost;
    @Autowired
    YSClinet ysClinet;

    @RequestMapping(value = URIEndpoints.PING, method = RequestMethod.GET)
    public ServiceResponse<String> getUserInfo(@RequestParam(value = "Hxzid") String Hxzid
    ) throws Exception {
//        String t = towerCraneClinet.getToken(""); 18082005
        String string = chenDuPost.pushTowerBasic("DEYE", Hxzid);
//        String string = haiNanPost.pushAlarmCfg("DEYE", Hxzid);
//        String string = haiNanPost.uploadImagelog("DEYE", Hxzid);
//        String string = haiNanPost.pushAlarmCfg("DEYE", Hxzid);
//        String string = ysClinet.getToken();
//        String string = haiNanPost.lifterWorkTime("DEYE", Hxzid);
//        String string = ysClinet.postCaptureAPI();
        return new ServiceResponse<String>(string, HttpStatus.OK);
    }
}
