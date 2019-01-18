package org.flowable.ui.modeler.service;

import org.flowable.ui.modeler.domain.ProcNodeInfo;
import org.flowable.ui.modeler.repository.ProcNodeRepository;
import org.flowable.ui.modeler.serviceapi.ProcNodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichao
 */
@Service
public class ProcNodeServiceImpl implements ProcNodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcNodeServiceImpl.class);

    @Autowired
    ProcNodeRepository procNodeRepository;

    @Override
    public void save(ProcNodeInfo procNodeInfo) {
        procNodeRepository.save(procNodeInfo);
    }

    @Override
    public String queryNodePreemptModeProcDefIdAndActId(String procDefId,String actId) {
        List<ProcNodeInfo> list = procNodeRepository.findNodePreemptModeProcDefIdAndActId(procDefId,actId);
        if(list!=null&&list.size()>0) {
            LOGGER.info("获取当前节点的抢占模式值!");
            return list.get(0).getIsPreemptMode();
        }else {
            return null;
        }
    }
    @Override
    public String queryNodePreemptModeProcDefKeyAndActId(String procDefKey,String actId) {
        List<ProcNodeInfo> list = procNodeRepository.findNodePreemptModeProcDefKeyAndActId(procDefKey,actId);
        if(list!=null&&list.size()>0) {
            LOGGER.info("获取当前节点的抢占模式值!");
            return list.get(0).getIsPreemptMode();
        }else {
            return null;
        }
    }
}
