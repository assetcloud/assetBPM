package com.yonyougov.flowable.service.runtime;

import org.junit.Test;

public class TaskTest {

    @Test
    public void test() {

       String userid = "123";
       String s =  "{\"id\":\""+userid+"\",\"firstName\":\"Test\",\"lastName\":\"Administrator\",\"email\":\"admin@flowable.org\","
                + "\"fullName\":\"Test Administrator\",\"groups\":[],\"privileges\""
                + ":["
                + "\"access-idm\","
                + "\"access-task\","
                + "\"access-modeler\","
                + "\"access-admin\""
                + "]}\n";
        System.out.println(s);
    }
}
