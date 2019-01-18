package com.yonyougov.flowable.thread;

/**
 * @author lichao
 */
public class ProcUsersThread implements Runnable{

    private String taskId;
    private String procInstId;
    private String procDefId;

    public ProcUsersThread(String taskId,String procInstId,String procDefId) {
        this.taskId = taskId;
        this.procInstId = procInstId;
        this.procDefId = procDefId;
    }

    @Override
    public synchronized void run() {
        //启动保存人员
        System.out.println(taskId);
        System.out.println(procInstId);
        System.out.println(procDefId);
    }
}
