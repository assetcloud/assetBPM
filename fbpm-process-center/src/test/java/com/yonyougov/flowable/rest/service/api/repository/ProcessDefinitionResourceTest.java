package com.yonyougov.flowable.rest.service.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.yonyougov.flowable.rest.service.api.BaseSpringRestTest;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.flowable.rest.service.api.RestUrls;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProcessDefinitionResourceTest extends BaseSpringRestTest {

    @Test
    public void getProcessDefinitionsTest() throws Exception{
        String baseUrl = RestUrls.createRelativeResourceUrl(RestUrls.URL_PROCESS_DEFINITION_COLLECTION);
        String url = SERVER_URL_PREFIX + baseUrl;
        CloseableHttpResponse response = executeRequest(new HttpGet(url), HttpStatus.SC_OK);
        JsonNode dataNode = objectMapper.readTree(response.getEntity().getContent()).get("data");
        closeResponse(response);
        for (int i = 0; i < dataNode.size(); i++) {
            JsonNode processDefinitionJson = dataNode.get(i);

            String key = processDefinitionJson.get("key").asText();
            JsonNode graphicalNotationNode = processDefinitionJson.get("graphicalNotationDefined");
            if (key.equals("oneTaskProcessWithDi")) {
                assertTrue(graphicalNotationNode.asBoolean());
            } else {
                assertFalse(graphicalNotationNode.asBoolean());
            }
        }
    }
}
