package org.flowable.ui.modeler.service;

import org.flowable.ui.modeler.domain.ProcRuIdentityInfo;
import org.flowable.ui.modeler.repository.ProcRuIdentityRepository;
import org.flowable.ui.modeler.serviceapi.ProcRuIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lichao
 */
@Service
public class ProcRuIdentityServiceImpl implements ProcRuIdentityService {

    @Autowired
    ProcRuIdentityRepository procRuIdentityRepository;

    @Override
    public void save(ProcRuIdentityInfo procRuIdentityInfo) {
        procRuIdentityRepository.insert(procRuIdentityInfo);
    }
}
