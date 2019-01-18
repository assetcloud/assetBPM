package org.flowable.ui.modeler.domain;

/**
 * 设计态节点身份信息
 * @author lichao
 */
public class ProcReIdentityInfo {

    private String id;
    private String actId;
    private String actName;
    private String procDefId;
    private String procDefKey;
    /**
     * 审批身份状态
     * 1、角色 2、用户组 3、组织 4、岗位 5、用户
     */
    private String identityType;
    private String identityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }
}
