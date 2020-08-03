package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;

@RestController
public class DemoController {
    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(value = "/echo/{param}", method = RequestMethod.GET)
    public String echo(@PathVariable String param) throws Exception {
        LOG.info("provider-demo -- request param: [" + param + "]");
        String result = "request param: " + param + ", response from " + (InetAddress.getLocalHost().getHostAddress()).trim();
        LOG.info("provider-demo -- response info: [" + result + "]");
        return result;
    }

}
