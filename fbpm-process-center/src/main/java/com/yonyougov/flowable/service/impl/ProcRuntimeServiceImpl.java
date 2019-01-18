package com.yonyougov.flowable.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yonyougov.flowable.dao.ProcRuntimeMapper;
import com.yonyougov.flowable.entity.ProcInstInfo;
import com.yonyougov.flowable.service.ProcRuntimeService;
import com.yonyougov.flowable.utils.PageGrids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichao
 */
@SuppressWarnings("Duplicates")
@Service
public class ProcRuntimeServiceImpl implements ProcRuntimeService {

    @Autowired
    ProcRuntimeMapper procRuntimeMapper;

    @Override
    public PageGrids getProcInstPages(Integer pageNum, Integer pageSize, String id_, String procDefId) {

        PageGrids pageGrids = new PageGrids();
        PageHelper.startPage(pageNum,pageSize);
        List<ProcInstInfo> list = procRuntimeMapper.selectProcInstPages(id_,procDefId);
        PageInfo<ProcInstInfo> pageInfo = new PageInfo<ProcInstInfo>(list);
        //总页数
        pageGrids.setTotal(pageInfo.getPages());
        pageGrids.setRecords(pageInfo.getTotal());
        pageGrids.setPage(pageInfo.getPageNum());
        pageGrids.setRows(list);
        return pageGrids;
    }
}
