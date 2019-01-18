package com.yonyougov.flowable.controller;

import com.crux.core.context.AppContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author lichao
 */
@RestController
@RequestMapping("/api")
public class FlowableAccountController {

	@Autowired
	AppContext appContext;

	@RequestMapping(value = "/rest/account2")
	public String account() {
		String userid = "admin";
		if(appContext.currentUser()!=null) {
			userid = String.valueOf(appContext.currentUser());
		}
		return "{\"id\":\""+userid+"\",\"firstName\":\"Test\",\"lastName\":\"Administrator\",\"email\":\"admin@flowable.org\","
				+ "\"fullName\":\"Test Administrator\",\"groups\":[],\"privileges\""
				+ ":["
				+ "\"access-idm\","
				+ "\"access-task\","
				+ "\"access-modeler\","
				+ "\"access-admin\""
				+ "]}\n";
	}
}
