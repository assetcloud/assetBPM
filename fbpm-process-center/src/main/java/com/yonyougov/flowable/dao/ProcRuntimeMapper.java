package com.yonyougov.flowable.dao;

import com.yonyougov.flowable.entity.ProcInstInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author lichao
 */
@Repository
public interface ProcRuntimeMapper {

    /**
     * 查询流程实例数据
     * @param id_
     * @param procDefId
     * @return List<ProcInstInfo>
     */
    List<ProcInstInfo> selectProcInstPages(@Param("id_") String id_, @Param("procDefId")  String procDefId);
}
