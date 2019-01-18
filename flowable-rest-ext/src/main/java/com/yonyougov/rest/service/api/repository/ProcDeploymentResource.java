package com.yonyougov.rest.service.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonyougov.rest.converter.HmBpmnJsonConverter;
import com.yonyougov.rest.converter.HmUserTaskJsonConverter;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.domain.ProcNodeInfo;
import org.flowable.ui.modeler.domain.ProcReIdentityInfo;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.flowable.ui.modeler.serviceapi.ProcNodeService;
import org.flowable.ui.modeler.serviceapi.ProcReIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lichao
 */
@SuppressWarnings("Duplicates")
@RestController
public class ProcDeploymentResource {

    @Autowired
    ProcNodeService procNodeService;
    @Autowired
    ModelService modelService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ProcReIdentityService procReIdentityService;

    @PostMapping(value = "/repository/procdeploy/{modelId}",produces = "application/json")
    @ResponseBody
    public ProcDeploymentResponse procDeploy(@PathVariable String modelId, HttpServletRequest request, HttpServletResponse response) throws Exception{
        if(StringUtils.isBlank(modelId)) {
            throw new FlowableIllegalArgumentException("流程部署时,modelId不能为空!");
        }
        //获取模型
        Model modelData = modelService.getModel(modelId);
        JsonNode modelNode = new ObjectMapper().readTree(modelData.getModelEditorJson());
        HmBpmnJsonConverter.getConvertersToBpmnMap().put("UserTask", HmUserTaskJsonConverter.class);
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        BpmnModel model = jsonConverter.convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0) {
            throw new FlowableException("流程模型不符要求，请至少设计一条主线流程!");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        String deployId = deployment.getId();
        //解析流程图
        if(StringUtils.isNotEmpty(deployId)) {
            parseBpmModel(deployId,model);
            ProcDeploymentResponse procDeploymentResponse = new ProcDeploymentResponse();
            procDeploymentResponse.setDeployId(deployment.getId());
            procDeploymentResponse.setDeployName(deployment.getName());
            response.setStatus(HttpStatus.CREATED.value());
            return procDeploymentResponse;
        }else {
            //部署失败
            return null;
        }
    }

    /**
     * 流程图解析
     * @param deployId
     * @param bpmnModel
     */
    public void parseBpmModel(String deployId,BpmnModel bpmnModel) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployId)
                .singleResult();
        if(processDefinition!=null) {
            //获取流程定义Id
            String procDefId = processDefinition.getId();
            //解析
            Process process = bpmnModel.getMainProcess();
            Collection<FlowElement> flowElements = process.getFlowElements();
            for(FlowElement element:flowElements) {
                //TODO 暂时过滤掉线类型，后期做流程线扩展
                if(element instanceof SequenceFlow) {
                    continue;
                }
                //流程图节点属性
                ProcNodeInfo entity = new ProcNodeInfo();
                //流程定义Id
                entity.setProcDefId(procDefId);
                entity.setActId(element.getId());
                entity.setCreateTime(new Date());
                //用户任务节点类型
                if(element instanceof UserTask)  {
                    UserTask userTask = (UserTask) element;
                    entity.setActName(element.getName());
                    entity.setActType("userTask");
                    //判断是否多实例节点,多实例节点标记为1
                    if(userTask.getLoopCharacteristics()!=null) {
                        entity.setIsMultiinstance("1");
                    }else {
                        entity.setIsMultiinstance("0");
                    }
                    //获取物理子节点
//                    String physicsChildIds = procDefService.getPhysicsChildIds(userTask);
//                    if(StringUtils.isNotBlank(physicsChildIds)) {
//                        entity.setPhysicsChildId(physicsChildIds);
//                    }
                    //解析设计态审批身份
                    saveNodeIdentity(userTask,procDefId);
                    //解析是否抢占模式
                    List<ExtensionAttribute> extensionAttributes = userTask.getAttributes().get("flowable-ext");
                    if(extensionAttributes!=null&&extensionAttributes.size()>0) {
                        for(ExtensionAttribute extensionAttribute:extensionAttributes) {
                            if(extensionAttribute.getName()!=null && "preemptmode".equals(extensionAttribute.getName())) {
                                //此处 0：非抢占模式 1：强占模式
                                entity.setIsPreemptMode(extensionAttribute.getName());
                            }
                        }
                    }
                }else if(element instanceof StartEvent)  {
                    if(StringUtils.isBlank(element.getName())) {
                        entity.setActName("开始");
                    }else {
                        entity.setActName(element.getName());
                    }
                    entity.setIsMultiinstance("0");
                    entity.setActType("startEvent");
                }else if(element instanceof EndEvent)  {
                    if(StringUtils.isBlank(element.getName())) {
                        entity.setActName("结束");
                    }else {
                        entity.setActName(element.getName());
                    }
                    entity.setIsMultiinstance("0");
                    entity.setActType("endEvent");
                }
                procNodeService.save(entity);
            }
        }
    }

    public void saveNodeIdentity(UserTask userTask,String procDefId) {
        List<ExtensionAttribute> extensionAttributes = userTask.getAttributes().get("flowable-ext");
        if(extensionAttributes!=null&&extensionAttributes.size()>0) {
            for(ExtensionAttribute extensionAttribute:extensionAttributes) {
                ProcReIdentityInfo entity = new ProcReIdentityInfo();
                entity.setActId(userTask.getId());
                entity.setActName(userTask.getName());
                entity.setProcDefId(procDefId);
                if(extensionAttribute.getName()!=null && "userids".equals(extensionAttribute.getName())) {
                    //5：用户
                    entity.setIdentityType("5");
                    entity.setIdentityId(extensionAttribute.getName());
                }else if(extensionAttribute.getName()!=null && "roleids".equals(extensionAttribute.getName())) {
                    //1：角色
                    entity.setIdentityType("1");
                    entity.setIdentityId(extensionAttribute.getName());
                }else if(extensionAttribute.getName()!=null && "orgids".equals(extensionAttribute.getName())) {
                    //3:组织
                    entity.setIdentityType("3");
                    entity.setIdentityId(extensionAttribute.getName());
                }
                if(entity.getIdentityId()!=null) {
                    procReIdentityService.save(entity);
                }
            }
        }
    }

}
