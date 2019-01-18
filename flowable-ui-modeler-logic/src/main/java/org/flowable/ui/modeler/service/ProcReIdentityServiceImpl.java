package org.flowable.ui.modeler.service;

import org.flowable.ui.modeler.domain.ProcReIdentityInfo;
import org.flowable.ui.modeler.repository.ProcReIdentityRepository;
import org.flowable.ui.modeler.serviceapi.ProcReIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lichao
 */
@Service
public class ProcReIdentityServiceImpl implements ProcReIdentityService {

    @Autowired
    ProcReIdentityRepository procReIdentityRepository;

    @Override
    public void save(ProcReIdentityInfo procReIdentityInfo) {
        procReIdentityRepository.insert(procReIdentityInfo);
    }
}
