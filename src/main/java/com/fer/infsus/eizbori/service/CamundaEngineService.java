package com.fer.infsus.eizbori.service;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CamundaEngineService {

    private final HistoryService historyService;
    private final IdentityService identityService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    @Autowired
    public CamundaEngineService(HistoryService historyService, IdentityService identityService, RuntimeService runtimeService, TaskService taskService) {
        this.historyService = historyService;
        this.identityService = identityService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    public boolean isUserInGroup(String userId, String groupId) {
        return !identityService
                .createUserQuery()
                .userId(userId)
                .memberOfGroup(groupId)
                .list()
                .isEmpty();
    }

    public List<User> getUsersInGroup(String groupId) {
        return identityService
                .createUserQuery()
                .memberOfGroup(groupId)
                .list();
    }

    public List<HistoricProcessInstance> getProcessInstances(String processDefinitionKey) {
        return historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .orderByProcessInstanceStartTime()
                .desc()
                .list();
    }

    public List<HistoricVariableInstance> getProcessInstanceVariables(String processInstanceId) {
        return historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
    }

    public void startProcessInstance(String processDefinitionKey, Map<String, Object> variables) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    }

    public void sendMessage(String message, String processInstanceId, Map<String, Object> variables) {
        runtimeService
                .createMessageCorrelation(message)
                .processInstanceId(processInstanceId)
                .setVariables(variables)
                .correlateAll();
    }

    public List<Task> getUserTasks(String user, String processDefinitionKey) {
        return taskService
                .createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(user)
                .orderByTaskCreateTime()
                .desc()
                .list();
    }

    public List<Task> getUnassignedGroupTasks(String group, String processDefinitionKey) {
        return taskService
                .createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskUnassigned()
                .taskCandidateGroup(group)
                .orderByTaskCreateTime()
                .desc()
                .list();
    }

    public Map<String, Object> getTaskVariables(String taskId) {
        return taskService.getVariables(taskId);
    }

    public void claimTask(String taskId, String userId) {
        taskService.claim(taskId, userId);
    }

    public void completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }
}
