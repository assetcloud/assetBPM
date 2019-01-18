package com.yonyougov.flowable.feign;

import com.yonyougov.flowable.entity.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author lichao
 */
@FeignClient(name = "permission",path = "/permission")
public interface PermissionApi {
    /**
     * 根据角色Id获取人员信息的名称
     * @param roleId
     * @return
     */
    @GetMapping("/api/userrole?role.id={roleId}&fetchProperties=user[id,name]")
    List<UserInfo> getUserRole(@PathVariable("roleId") String roleId);
}
