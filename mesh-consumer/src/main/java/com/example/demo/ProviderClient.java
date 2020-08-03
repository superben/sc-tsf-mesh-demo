package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "mesh-provider", url = "http://mesh-provider:18081", fallback = FeignClientFallback.class)
public interface ProviderClient {
    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
    String echo(@RequestHeader Map<String, Object> headers, @PathVariable("str") String str);
}

@Component
class FeignClientFallback implements ProviderClient {
    @Override
    public String echo(Map<String, Object> headers, String str) {
        return "hystrix-" + str;
    }
}