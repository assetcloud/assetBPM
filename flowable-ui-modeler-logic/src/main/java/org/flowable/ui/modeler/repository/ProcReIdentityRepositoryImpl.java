package org.flowable.ui.modeler.repository;

import org.flowable.ui.modeler.domain.ProcReIdentityInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lichao
 */
@Component
public class ProcReIdentityRepositoryImpl implements ProcReIdentityRepository {

    private static final String NAMESPACE = "org.flowable.ui.modeler.domain.ProcReIdentityInfo";

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void insert(ProcReIdentityInfo procReIdentityInfo) {
        if(procReIdentityInfo.getId()==null) {
            sqlSessionTemplate.insert(NAMESPACE+ "insertProcReIdentityInfo",procReIdentityInfo);
        }else {
            sqlSessionTemplate.insert(NAMESPACE+ "updateProcReIdentityInfo",procReIdentityInfo);
        }
    }
}
