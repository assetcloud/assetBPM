package com.yonyougov.rest.service.api.runtime;

import io.swagger.annotations.*;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.rest.api.DataResponse;
import org.flowable.rest.service.api.engine.variable.RestVariable;
import org.flowable.rest.service.api.runtime.task.TaskActionRequest;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.ui.modeler.domain.ProcRuIdentityInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lichao
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/workflow/api")
@Api(tags = { "Tasks" }, description = "流程任务", authorizations = { @Authorization(value = "basicAuth") })
public class ProcTaskResource extends ProcTaskBaseResource {

    @ApiOperation(value = "Query for tasks", nickname="queryTasks", tags = {"Tasks", "Query"},
            notes = "All supported JSON parameter fields allowed are exactly the same as the parameters found for getting a collection of tasks (except for candidateGroupIn which is only available in this POST task query REST service), but passed in as JSON-body arguments rather than URL-parameters to allow for more advanced querying and preventing errors with request-uri’s that are too long. On top of that, the query allows for filtering based on task and process variables. The taskVariables and processInstanceVariables are both JSON-arrays containing objects with the format as described here.")
    @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Indicates request was successful and the tasks are returned."),
    @ApiResponse(code = 400, message = "Indicates a parameter was passed in the wrong format or that delegationState has an invalid value (other than pending and resolved). The status-message contains additional information.")
    })
    @PostMapping(value = "/query/proctasks", produces = "application/json")
    public DataResponse<ProcTaskResponse> getQueryResult(@RequestBody ProcTaskQueryRequest request, @ApiParam(hidden = true) @RequestParam Map<String, String> requestParams, HttpServletRequest httpRequest) {

        return getTasksFromQueryRequest(request, requestParams);
    }

    @PostMapping(value = "/runtime/proctasks/{taskId}")
    @ResponseStatus(value = HttpStatus.OK)
    public void executeTaskAction(@ApiParam(name = "taskId") @PathVariable String taskId, @RequestBody TaskActionRequest actionRequest) {
        if (actionRequest == null) {
            throw new FlowableException("A request body was expected when executing a task action.");
        }

        Task task = getTaskFromRequest(taskId);

        if (restApiInterceptor != null) {
            restApiInterceptor.executeTaskAction(task, actionRequest);
        }

        if (TaskActionRequest.ACTION_COMPLETE.equals(actionRequest.getAction())) {
            completeTask(task, actionRequest);

        } else if (TaskActionRequest.ACTION_CLAIM.equals(actionRequest.getAction())) {
            claimTask(task, actionRequest);

        } else if (TaskActionRequest.ACTION_DELEGATE.equals(actionRequest.getAction())) {
            delegateTask(task, actionRequest);

        } else if (TaskActionRequest.ACTION_RESOLVE.equals(actionRequest.getAction())) {
            resolveTask(task, actionRequest);

        } else {
            throw new FlowableIllegalArgumentException("Invalid action: '" + actionRequest.getAction() + "'.");
        }
    }

    protected void completeTask(Task task, TaskActionRequest actionRequest) {
        Map<String, Object> variablesToSet = null;
        Map<String, Object> transientVariablesToSet = null;

        if (actionRequest.getVariables() != null) {
            variablesToSet = new HashMap<>();
            for (RestVariable var : actionRequest.getVariables()) {
                if (var.getName() == null) {
                    throw new FlowableIllegalArgumentException("Variable name is required");
                }
                Object actualVariableValue = procRestResponseFactory.getVariableValue(var);
                variablesToSet.put(var.getName(), actualVariableValue);
            }
            /**
             * TODO 添加节点审批人员参数 By-lichao
             */
            if(variablesToSet.get("userids")==null) {
                variablesToSet.put("userids",null);
            }
        }

        if (actionRequest.getTransientVariables() != null) {
            transientVariablesToSet = new HashMap<>();
            for (RestVariable var : actionRequest.getTransientVariables()) {
                if (var.getName() == null) {
                    throw new FlowableIllegalArgumentException("Transient variable name is required");
                }

                Object actualVariableValue = procRestResponseFactory.getVariableValue(var);
                transientVariablesToSet.put(var.getName(), actualVariableValue);
            }
        }

        taskService.complete(task.getId(), variablesToSet, transientVariablesToSet);
        /**
         * TODO 取缓存的节点信息,如果是抢占模式单独处理 By-lichao
         * TODO 暂时查询数据库
         */
//        List<Task> tasks1 = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
////        //如果是非会签模式做 抢占模式处理，否则不做抢占模式处理
////        if(tasks1!=null&&tasks1.size()==1) {
////            Task newTask = tasks1.get(0);
////            String preemptMode = procNodeService.queryNodePreemptModeProcDefIdAndActId(task.getProcessDefinitionId(),newTask.getTaskDefinitionKey());
////            //抢占模式
////            if("1".equals(preemptMode)) {
////                ProcRuIdentityInfo entity = new ProcRuIdentityInfo();
//                //设置实体属性
//                entity.setProcDefId(newTask.getProcessDefinitionId());
//                procRuIdentityService.save(entity);
//            }
//        }
    }

    protected void resolveTask(Task task, TaskActionRequest actionRequest) {
        taskService.resolveTask(task.getId());
    }

    protected void delegateTask(Task task, TaskActionRequest actionRequest) {
        if (actionRequest.getAssignee() == null) {
            throw new FlowableIllegalArgumentException("An assignee is required when delegating a task.");
        }
        taskService.delegateTask(task.getId(), actionRequest.getAssignee());
    }

    protected void claimTask(Task task, TaskActionRequest actionRequest) {
        // In case the task is already claimed, a
        // FlowableTaskAlreadyClaimedException is thrown and converted to
        // a CONFLICT response by the ExceptionHandlerAdvice
        taskService.claim(task.getId(), actionRequest.getAssignee());
    }
}
