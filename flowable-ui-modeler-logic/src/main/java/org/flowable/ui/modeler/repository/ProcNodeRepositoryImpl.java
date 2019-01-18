package org.flowable.ui.modeler.repository;

import org.flowable.ui.modeler.domain.ProcNodeInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lichao
 */
@Component
public class ProcNodeRepositoryImpl implements ProcNodeRepository {

    private static final String NAMESPACE = "org.flowable.ui.modeler.domain.ProcNodeInfo.";

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;


    @Override
    public void save(ProcNodeInfo procNodeInfo) {
        if(procNodeInfo.getId()==null) {
            sqlSessionTemplate.insert(NAMESPACE+ "insertProcNodeInfo",procNodeInfo);
        }else {
            sqlSessionTemplate.update(NAMESPACE +"updateProcNodeInfo",procNodeInfo);
        }
    }

    @Override
    public List<ProcNodeInfo> findNodePreemptModeProcDefIdAndActId(String procDefId, String actId) {
        Map<String, Object> params = new HashMap<>();
        params.put("procDefId", procDefId);
        params.put("actId", actId);
        return sqlSessionTemplate.selectList(NAMESPACE+"findNodePreemptModeProcDefIdAndActId",params);
    }

    @Override
    public List<ProcNodeInfo> findNodePreemptModeProcDefKeyAndActId(String procDefKey, String actId) {
        Map<String, Object> params = new HashMap<>();
        params.put("procDefKey", procDefKey);
        params.put("actId", actId);
        return sqlSessionTemplate.selectList(NAMESPACE+"findNodePreemptModeProcDefKeyAndActId",params);
    }
}
