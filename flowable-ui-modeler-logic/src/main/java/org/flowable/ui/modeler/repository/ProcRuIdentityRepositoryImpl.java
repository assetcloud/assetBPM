package org.flowable.ui.modeler.repository;

import org.flowable.ui.modeler.domain.ProcRuIdentityInfo;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lichao
 */
@Component
public class ProcRuIdentityRepositoryImpl implements ProcRuIdentityRepository {

    private static final String NAMESPACE = "org.flowable.ui.modeler.domain.ProcRuIdentityInfo";

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void insert(ProcRuIdentityInfo procRuIdentityInfo) {
        if(procRuIdentityInfo.getId()==null) {
            sqlSessionTemplate.insert(NAMESPACE+ "insertProcRuIdentityInfo",procRuIdentityInfo);
        }else {
            sqlSessionTemplate.insert(NAMESPACE+ "updateProcRuIdentityInfo",procRuIdentityInfo);
        }
    }
}
