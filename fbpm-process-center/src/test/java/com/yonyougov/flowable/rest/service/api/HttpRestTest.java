package com.yonyougov.flowable.rest.service.api;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.flowable.engine.impl.util.IdentityLinkUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpRestTest extends BaseSpringRestTest{

    @Test
    public void test() throws Exception {
        String baseUrl = "/workflow/api/runtime/geProcessTasks1";
        String url = SERVER_URL_PREFIX + baseUrl;
        CloseableHttpResponse response = executeRequest(new HttpGet(url), HttpStatus.SC_OK);
        System.out.println("---value:" + response.getEntity().getContent().toString());
    }

    @Test
    public void test1() {
        Map<String, Object> startVariables = new HashMap<>();
        //startVariables.put("a","123");
        if(startVariables.get("userids")==null) {
            startVariables.put("userids",null);
        }
        System.out.println();
    }
}
