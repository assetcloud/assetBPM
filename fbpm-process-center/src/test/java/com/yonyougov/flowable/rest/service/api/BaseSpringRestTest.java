package com.yonyougov.flowable.rest.service.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.fail;

public class BaseSpringRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseSpringRestTest.class);
    protected ObjectMapper objectMapper = new ObjectMapper();

    protected static String SERVER_URL_PREFIX = "http://127.0.0.1:8081/workflow/api/";
    protected static CloseableHttpClient client;
    protected static LinkedList<CloseableHttpResponse> httpResponses = new LinkedList<>();


    @Before
    public void init() {
        //
        createHttpClient();
    }

    public void closeResponse(CloseableHttpResponse response) {
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                fail("Could not close http connection", e);
            }
        }
    }

    public CloseableHttpResponse executeRequest(HttpUriRequest request, int expectedStatusCode) {
        return internalExecuteRequest(request, expectedStatusCode, true);
    }

    protected CloseableHttpResponse internalExecuteRequest(HttpUriRequest request, int expectedStatusCode, boolean addJsonContentType) {
        CloseableHttpResponse response = null;
        try {
            if (addJsonContentType && request.getFirstHeader(HttpHeaders.CONTENT_TYPE) == null) {
                // Revert to default content-type
                request.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8"));
            }
            response = client.execute(request);
            Assert.assertNotNull(response.getStatusLine());

            int responseStatusCode = response.getStatusLine().getStatusCode();
            if (expectedStatusCode != responseStatusCode) {
                LOGGER.info("Wrong status code : {}, but should be {}", responseStatusCode, expectedStatusCode);
                LOGGER.info("Response body: {}", IOUtils.toString(response.getEntity().getContent()));
            }

            Assert.assertEquals(expectedStatusCode, responseStatusCode);
            httpResponses.add(response);
            return response;

        } catch (IOException e) {
            fail(e.getMessage(), e);
        }
        return null;
    }

    protected void createHttpClient() {

        if (client != null) {
            return;
        }
        client = HttpClientBuilder.create().build();
    }
}

