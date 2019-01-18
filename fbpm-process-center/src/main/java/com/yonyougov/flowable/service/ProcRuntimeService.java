package com.yonyougov.flowable.service;

import com.yonyougov.flowable.utils.PageGrids;

/**
 * @author lichao
 */
public interface ProcRuntimeService {

    /**
     * 流程实例查询分页
     * @param pageNum
     * @param pageSize
     * @param id_
     * @param procDefId
     * @return
     */
    PageGrids getProcInstPages(Integer pageNum, Integer pageSize, String id_, String procDefId);
}
