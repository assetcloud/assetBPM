package org.flowable.ui.modeler.repository;

import org.flowable.ui.modeler.domain.ProcNodeInfo;

import java.util.List;

/**
 * @author lichao
 */
public interface ProcNodeRepository {

    /**
     * 保存节点信息
     * @param procNodeInfo
     */
    void save(ProcNodeInfo procNodeInfo);

    /**
     * 根据procDefId和节点Id查询当前节点是否是抢占模式
     * @param procDefId
     * @param actId
     * @return
     */
    List<ProcNodeInfo> findNodePreemptModeProcDefIdAndActId(String procDefId, String actId);

    /**
     *  据procDefKey和节点Id查询当前节点是否是抢占模式
     * @param procDefKey
     * @param actId
     * @return
     */
    List<ProcNodeInfo> findNodePreemptModeProcDefKeyAndActId(String procDefKey, String actId);
}
