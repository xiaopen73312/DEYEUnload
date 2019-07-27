package com.deye.xl.controllers;


import com.deye.xl.constants.URIEndpoints;
import com.deye.xl.model.ServiceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = URIEndpoints.TEST)
public class TestController {


    @RequestMapping(value = URIEndpoints.PING, method = RequestMethod.GET)
    public ServiceResponse<String> getUserInfo(
    ) throws Exception {

        return new ServiceResponse<String>("ping", HttpStatus.OK);
    }
}
