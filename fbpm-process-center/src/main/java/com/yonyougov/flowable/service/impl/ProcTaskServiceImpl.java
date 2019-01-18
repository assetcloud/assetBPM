package com.yonyougov.flowable.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yonyougov.flowable.dao.ProcTaskMapper;
import com.yonyougov.flowable.entity.ProcTaskInfo;
import com.yonyougov.flowable.service.ProcTaskService;
import com.yonyougov.flowable.utils.PageGrids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichao
 */
@SuppressWarnings("Duplicates")
@Service
public class ProcTaskServiceImpl implements ProcTaskService {

    @Autowired
    ProcTaskMapper procTaskMapper;

    @Override
    public PageGrids getToDoTaskPages(Integer pageNum, Integer pageSize, String id_, String assignee) {
        PageGrids pageGrids = new PageGrids();
        PageHelper.startPage(pageNum,pageSize);
        List<ProcTaskInfo> list = procTaskMapper.selectToDoTaskPages(id_,assignee);
        PageInfo<ProcTaskInfo> pageInfo = new PageInfo<ProcTaskInfo>(list);
        //总页数
        pageGrids.setTotal(pageInfo.getPages());
        pageGrids.setRecords(pageInfo.getTotal());
        pageGrids.setPage(pageInfo.getPageNum());
        pageGrids.setRows(list);
        return pageGrids;
    }

    @Override
    public PageGrids getCompletePages(Integer pageNum, Integer pageSize, String id_, String assignee) {
        PageGrids pageGrids = new PageGrids();
        PageHelper.startPage(pageNum,pageSize);
        List<ProcTaskInfo> list = procTaskMapper.selectCompleteTaskPages(id_,assignee);
        PageInfo<ProcTaskInfo> pageInfo = new PageInfo<ProcTaskInfo>(list);
        //总页数
        pageGrids.setTotal(pageInfo.getPages());
        pageGrids.setRecords(pageInfo.getTotal());
        pageGrids.setPage(pageInfo.getPageNum());
        pageGrids.setRows(list);
        return pageGrids;
    }
}
