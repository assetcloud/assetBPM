package com.yonyougov.flowable.service.runtime;

import com.yonyougov.flowable.FbpmApplicationTests;
import org.flowable.engine.TaskService;
import org.flowable.identitylink.api.IdentityLinkType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskIdentityLinkTest  extends FbpmApplicationTests {

    @Autowired
    TaskService taskService;

    @Test
    public void testIdentityUsers() {
        String taskId = "80d94a0f-07f0-11e9-beec-1ab7ac0644c5";
        taskService.addUserIdentityLink(taskId,"adminT1", IdentityLinkType.CANDIDATE);
    }
}
