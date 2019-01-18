package org.flowable.ui.modeler.serviceapi;

import org.flowable.ui.modeler.domain.ProcNodeInfo;

/**
 * @author lichao
 */
public interface ProcNodeService {

    void save(ProcNodeInfo procNodeInfo);

    /**
     * 查询节点是否是枪战模式
     * @param procDefId
     * @param actId
     * @return
     */
    String queryNodePreemptModeProcDefIdAndActId(String procDefId,String actId);
    /**
     * 查询节点是否是枪战模式
     * @param procDefId
     * @param actId
     * @return
     */
    String queryNodePreemptModeProcDefKeyAndActId(String procDefKey,String actId);
}
