package com.yonyougov.flowable.controller;

import com.yonyougov.flowable.entity.User;
import com.yonyougov.flowable.entity.UserInfo;
import com.yonyougov.flowable.feign.PermissionApi;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lichao
 */
@Controller
public class ProcessController {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ModelService modelService;

    @RequestMapping("/getProcDetails")
    public ModelAndView getProcDetails(@RequestParam String procDefId) throws Exception{
        ModelAndView mv = new ModelAndView();
        mv.addObject("procDefId",procDefId);
        String xml = getXmlDetailPage(procDefId);
        mv.addObject("fxml",xml);
        mv.setViewName("/pages/procdef/procdef_details");
        return mv;
    }

    @RequestMapping(value = "/showImage")
    public void showImage(@RequestParam String procDefId, HttpServletResponse response) throws Exception{
        BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
        List<String> highLightedActivities = new ArrayList<>();
        InputStream in = defaultProcessDiagramGenerator.generateDiagram(bpmnModel, "PNG", highLightedActivities, true);
        OutputStream out = response.getOutputStream();
        copyPic(in, out);
    }

    public String getXmlDetailPage(String procDefId) throws Exception {
        ModelAndView mv = new ModelAndView();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
        ByteArrayInputStream in = new ByteArrayInputStream(bpmnBytes);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4000];
        int length;
        while((length = in.read(buffer))!= -1) {
            bos.write(buffer,0,length);
        }
        in.close();
        String fxml = new String(bos.toByteArray(),"utf-8");
        return fxml;
    }

    private void copyPic(InputStream in, OutputStream out) {
        try {
            IOUtils.copy(in, out);
        } catch (IOException e) {
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(in);
        }
    }

    @Autowired
    PermissionApi permissionApi;

    @RequestMapping("/getUserByRoleId")
    @ResponseBody
    public List<UserInfo> getUserByRoleId() {
        String roleId = "5";
        List<UserInfo> list = permissionApi.getUserRole(roleId);
        for(UserInfo info:list) {
                System.out.println("user.getId() = "+info.getUser().getId()+" user.getName() = " + info.getUser().getName());
        }
        return list;
    }
}
