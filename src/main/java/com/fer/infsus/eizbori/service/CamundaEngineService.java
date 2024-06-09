package com.fer.infsus.eizbori.service;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Map;

@Service
public class CamundaEngineService {

    private final HistoryService historyService;
    private final IdentityService identityService;
    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;

    @Autowired
    public CamundaEngineService(HistoryService historyService, IdentityService identityService, RepositoryService repositoryService, RuntimeService runtimeService, TaskService taskService) {
        this.historyService = historyService;
        this.identityService = identityService;
        this.repositoryService = repositoryService;
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

    public String getXmlDefinition(String processDefinitionKey) {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(processDefinitionKey)
                .latestVersion()
                .singleResult();

        if (processDefinition != null) {
            InputStream processModel = repositoryService.getProcessModel(processDefinition.getId());
            return convertInputStreamToString(processModel);
        }
        return null;
    }

    public List<HistoricProcessInstance> getProcessInstances(String processDefinitionKey) {
        return historyService
                .createHistoricProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .orderByProcessInstanceStartTime()
                .asc()
                .list();
    }

    public List<HistoricVariableInstance> getProcessInstanceVariables(String processInstanceId) {
        return historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
    }

    public String startProcessInstance(String processDefinitionKey, Map<String, Object> variables) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        return processInstance.getId();
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
                .asc()
                .list();
    }

    public List<Task> getUnassignedGroupTasks(String group, String processDefinitionKey) {
        return taskService
                .createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskUnassigned()
                .taskCandidateGroup(group)
                .orderByTaskCreateTime()
                .asc()
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

    private String convertInputStreamToString(InputStream inputStream) {
        try (StringWriter writer = new StringWriter()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                writer.append(line);
            }
            return writer.toString();
        } catch (IOException exception) {
            throw new RuntimeException("Failed to convert process model", exception);
        }
    }
}
