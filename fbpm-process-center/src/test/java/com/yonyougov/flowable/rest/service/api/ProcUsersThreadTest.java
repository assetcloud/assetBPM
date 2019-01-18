package com.yonyougov.flowable.rest.service.api;

import com.yonyougov.flowable.thread.ProcUsersThread;
import org.junit.Test;

public class ProcUsersThreadTest {

    @Test
    public void testProcUsers() {
        System.out.println("testProcUsers: start!");
        Thread thread = new Thread(new ProcUsersThread("a","b","c"));
        thread.start();
        System.out.println("testProcUsers: end!");
    }
}
