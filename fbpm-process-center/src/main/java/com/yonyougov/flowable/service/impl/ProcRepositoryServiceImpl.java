package com.yonyougov.flowable.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yonyougov.flowable.dao.ProcRepositoryMapper;
import com.yonyougov.flowable.entity.ProcDefInfo;
import com.yonyougov.flowable.service.ProcRepositoryService;
import com.yonyougov.flowable.utils.PageGrids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichao
 */
@SuppressWarnings("Duplicates")
@Service
public class ProcRepositoryServiceImpl implements ProcRepositoryService {

    @Autowired
    ProcRepositoryMapper procRepositoryMapper;

    @Override
    public PageGrids getProcDefPages(Integer pageNum,Integer pageSize,String id_,String name_) {
        PageGrids pageGrids = new PageGrids();
        PageHelper.startPage(pageNum,pageSize);
        List<ProcDefInfo> list = procRepositoryMapper.selectProcDefPages(id_,name_);
        PageInfo<ProcDefInfo> pageInfo = new PageInfo<ProcDefInfo>(list);
        //总页数
        pageGrids.setTotal(pageInfo.getPages());
        pageGrids.setRecords(pageInfo.getTotal());
        pageGrids.setPage(pageInfo.getPageNum());
        pageGrids.setRows(list);
        return pageGrids;
    }
}
