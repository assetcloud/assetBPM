package com.yonyougov.flowable.service.runtime;

import com.yonyougov.flowable.FbpmApplicationTests;
import com.yonyougov.flowable.entity.UserInfo;
import com.yonyougov.flowable.feign.PermissionApi;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessTest extends FbpmApplicationTests {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    PermissionApi permissionApi;

    @Test
    public void startProcessById() {
        String procDefId = "fwProcess1001:1:13ca3aa9-fc25-11e8-921f-de0d1cd07325";
        Map<String,Object> variables = new HashMap<String,Object>();
        variables.put("userids",null);
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(procDefId,variables);
        System.out.println(" processInstance.getId()= " + processInstance.getId());
    }
}