package com.yonyougov.flowable.rest.service.api.runtime;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yonyougov.flowable.rest.service.api.BaseSpringRestTest;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.flowable.rest.service.api.RestUrls;
import org.junit.Test;

public class ProcessInstanceResourceTest extends BaseSpringRestTest {

    /**
     * 根据procDefId 启动流程
     * @throws Exception
     */
    @Test
    public void startProcessById() throws Exception{
        String processDefinitionId = "fwProcess1001:1:13ca3aa9-fc25-11e8-921f-de0d1cd07325";
        ObjectNode requestNode = objectMapper.createObjectNode();
        //单个参数
        requestNode.put("processDefinitionId", processDefinitionId);
        //多个参数
        ArrayNode variablesNode = objectMapper.createArrayNode();
//        //字符串
        ObjectNode stringVarNode = variablesNode.addObject();
        stringVarNode.put("name","userids");
        stringVarNode.put("value","admin");
        stringVarNode.put("type", "string");
        requestNode.set("variables", variablesNode);

        //启动流程url
        String url = RestUrls.createRelativeResourceUrl(RestUrls.URL_PROCESS_INSTANCE_COLLECTION);
        HttpPost httpPost = new HttpPost(SERVER_URL_PREFIX + url);
        httpPost.setEntity(new StringEntity(requestNode.toString()));
        CloseableHttpResponse response = executeRequest(httpPost, HttpStatus.SC_CREATED);

        JsonNode responseNode = objectMapper.readTree(response.getEntity().getContent());
        closeResponse(response);
        System.out.println(responseNode);
        System.out.println("---responseNode := " + responseNode);
    }

    /**
     * 根据Key启动流程
     * @throws Exception
     */
    @Test
    public void startProcessByKey() throws Exception{
        ObjectNode requestNode = objectMapper.createObjectNode();;
        requestNode.put("processDefinitionKey", "f23");

        HttpPost httpPost = new HttpPost(SERVER_URL_PREFIX + RestUrls.createRelativeResourceUrl(RestUrls.URL_PROCESS_INSTANCE_COLLECTION));
        httpPost.setEntity(new StringEntity(requestNode.toString()));
        CloseableHttpResponse response = executeRequest(httpPost, HttpStatus.SC_CREATED);
    }
}
