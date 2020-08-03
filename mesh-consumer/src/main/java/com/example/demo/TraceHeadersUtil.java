package com.example.demo;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraceHeadersUtil {

    private static final List<String> traceHeaders = new ArrayList<>();

    static {
        traceHeaders.add("x-request-id");
        traceHeaders.add("x-trace-service");
        traceHeaders.add("x-ot-span-context");
        traceHeaders.add("x-client-trace-id");
        traceHeaders.add("x-envoy-force-trace");

        traceHeaders.add("x-b3-traceid");
        traceHeaders.add("x-b3-spanid");
        traceHeaders.add("x-b3-parentspanid");
        traceHeaders.add("x-b3-sampled");
        traceHeaders.add("x-b3-flags");

        traceHeaders.add("testtag1");
        traceHeaders.add("test-tag2");
        traceHeaders.add("test_tag3");
        traceHeaders.add("TEST_TAG");
        traceHeaders.add("test{1}a");

        traceHeaders.add("te*st");

    }

    public static HttpHeaders buildTraceHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        for (String header : traceHeaders) {
            if (request.getHeader(header) != null) {
                headers.add(header, request.getHeader(header));
            }
        }
        headers.set("Accept", "text/plain");
        return headers;
    }

    public static Map<String, Object> buildTraceHeaderMap(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<>();
        for (String header : traceHeaders) {
            if (request.getHeader(header) != null) {
                headers.put(header, request.getHeader(header));
            }
        }
        headers.put("Accept", "text/plain");
        return headers;
    }
}
