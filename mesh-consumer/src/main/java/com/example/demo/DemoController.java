package com.example.demo;

import feign.HeaderMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Map;

@RestController
public class DemoController {
    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Qualifier("templateLB")
    @Autowired
    private RestTemplate rt;

    @Autowired
    private ProviderClient provider;

    @RequestMapping(value = "/echo-rest/{str}", method = RequestMethod.GET)
    public String restProvider(@PathVariable String str,
                               @RequestParam(required = false) String tagName,
                               @RequestParam(required = false) String tagValue,
                               HttpServletRequest servletRequest) {
        try {
            HttpHeaders headers = TraceHeadersUtil.buildTraceHeaders(servletRequest);
            headers.set("x-trace-service", "mesh-consumer");
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);

            return restTemplate.exchange("http://mesh-provider:18081/echo/" + str, HttpMethod.GET, requestEntity, String.class).getBody();
        } catch (Exception ex) {
            LOG.error("access provider service err", ex);
        }

        return "访问Provider异常";
    }

    @RequestMapping(value = "/echo-lb/{str}", method = RequestMethod.GET)
    public String restLB(@PathVariable String str,
                               @RequestParam(required = false) String tagName,
                               @RequestParam(required = false) String tagValue,
                               HttpServletRequest servletRequest) {
        try {
            HttpHeaders headers = TraceHeadersUtil.buildTraceHeaders(servletRequest);
            headers.set("x-trace-service", "mesh-consumer");
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);

            return rt.exchange("http://mesh-provider:18081/echo/" + str, HttpMethod.GET, requestEntity, String.class).getBody();
        } catch (Exception ex) {
            LOG.error("access provider service err", ex);
        }

        return "访问Provider异常";
    }


    @RequestMapping(value = "/echo-feign/{str}", method = RequestMethod.GET)
    public String feignProvider(@PathVariable String str,
                                @RequestParam(required = false) String tagName,
                                @RequestParam(required = false) String tagValue,
                                HttpServletRequest servletRequest) {

        Map<String, Object> headerMap = TraceHeadersUtil.buildTraceHeaderMap(servletRequest);
        headerMap.put("x-trace-service", "mesh-consumer");
        headerMap.put("application", "x-www-form-urlencoded");

        return provider.echo(headerMap, str);
    }

}
