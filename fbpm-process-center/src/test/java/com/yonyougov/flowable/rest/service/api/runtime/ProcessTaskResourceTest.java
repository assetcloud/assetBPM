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

public class ProcessTaskResourceTest  extends BaseSpringRestTest {

    /**
     * 审批任务
     */
    @Test
    public void approveTask() throws Exception{
        String taskId = "80d94a0f-07f0-11e9-beec-1ab7ac0644c5";

        ObjectNode requestNode = objectMapper.createObjectNode();
        //多个参数
        ArrayNode variablesNode = objectMapper.createArrayNode();
//        //字符串
        ObjectNode stringVarNode = variablesNode.addObject();
        stringVarNode.put("name","userids");
        stringVarNode.put("value","admin1");
        stringVarNode.put("type", "string");
        requestNode.set("variables", variablesNode);

        requestNode.put("action", "complete");
        String url = RestUrls.createRelativeResourceUrl(RestUrls.URL_TASK, taskId);
        HttpPost httpPost = new HttpPost(SERVER_URL_PREFIX + url);
        httpPost.setEntity(new StringEntity(requestNode.toString()));
        CloseableHttpResponse response = executeRequest(httpPost, HttpStatus.SC_OK);

        JsonNode responseNode = objectMapper.readTree(response.getEntity().getContent());
        closeResponse(response);
        System.out.println(responseNode);
        System.out.println("---responseNode := " + responseNode);
    }

    /**
     * 认领任务
     */
    @Test
    public void claimTask() throws Exception{
        String taskId = "03edd9a2-01f7-11e9-a82c-f2612af50ff7";
        ObjectNode requestNode = objectMapper.createObjectNode();
        //多个参数
        ArrayNode variablesNode = objectMapper.createArrayNode();
//        //字符串
        ObjectNode stringVarNode = variablesNode.addObject();
        stringVarNode.put("name","userids");
        stringVarNode.put("value","admin1");
        stringVarNode.put("type", "string");
        requestNode.set("variables", variablesNode);
        //认领任务
        requestNode.put("action", "claim");
        String url = RestUrls.createRelativeResourceUrl(RestUrls.URL_TASK, taskId);
        HttpPost httpPost = new HttpPost(SERVER_URL_PREFIX + url);
        httpPost.setEntity(new StringEntity(requestNode.toString()));
        CloseableHttpResponse response = executeRequest(httpPost, HttpStatus.SC_OK);

        JsonNode responseNode = objectMapper.readTree(response.getEntity().getContent());
        closeResponse(response);
        System.out.println(responseNode);
        System.out.println("---responseNode := " + responseNode);
    }

    /**
     * 代理任务
     */
    @Test
    public void delegateTask() throws Exception{
        String taskId = "03edd9a2-01f7-11e9-a82c-f2612af50ff7";
        ObjectNode requestNode = objectMapper.createObjectNode();
        //多个参数
        ArrayNode variablesNode = objectMapper.createArrayNode();
//        //字符串
        ObjectNode stringVarNode = variablesNode.addObject();
        stringVarNode.put("name","userids");
        stringVarNode.put("value","admin1");
        stringVarNode.put("type", "string");
        requestNode.set("variables", variablesNode);
        //认领任务
        requestNode.put("action", "claim");
        String url = RestUrls.createRelativeResourceUrl(RestUrls.URL_TASK, taskId);
        HttpPost httpPost = new HttpPost(SERVER_URL_PREFIX + url);
        httpPost.setEntity(new StringEntity(requestNode.toString()));
        CloseableHttpResponse response = executeRequest(httpPost, HttpStatus.SC_OK);

        JsonNode responseNode = objectMapper.readTree(response.getEntity().getContent());
        closeResponse(response);
        System.out.println(responseNode);
        System.out.println("---responseNode := " + responseNode);
    }
}
