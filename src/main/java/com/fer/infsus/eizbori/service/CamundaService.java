package com.fer.infsus.eizbori.service;

import com.fer.infsus.eizbori.model.CitizenRequestInfo;
import com.fer.infsus.eizbori.model.RequestReviewInfo;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CamundaService {

    private final String processKey = "ProcessRequest";

    private final CamundaEngineService camundaEngineService;

    @Autowired
    public CamundaService(CamundaEngineService camundaEngineService) {
        this.camundaEngineService = camundaEngineService;
    }

    public boolean isUserInGroup(String userId, String groupId) {
        return camundaEngineService.isUserInGroup(userId, groupId);
    }

    public List<User> getUsersInGroup(String groupId) {
        return camundaEngineService.getUsersInGroup(groupId);
    }

    public List<CitizenRequestInfo> getRequestsToCitizen(String userId) {
        return getRequestsByAuthor(userId, true).stream()
                .filter(request -> request.getPastDeadline() == null || !request.getPastDeadline())
                .sorted((r1, r2) -> r2.getDateCreated().compareTo(r1.getDateCreated()))
                .toList();
    }

    public List<CitizenRequestInfo> getUnassignedRequestsToAdmin(String userId) {
        return getRequestsByAuthor(userId, false).stream()
                .filter(request -> request.getPastDeadline() == null || !request.getPastDeadline())
                .filter(request -> request.getTimePassed() == null || !request.getTimePassed())
                .filter(request -> request.getReviewer() == null)
                .sorted((r1, r2) -> r2.getDateCreated().compareTo(r1.getDateCreated()))
                .toList();
    }

    public List<CitizenRequestInfo> getAssignedRequestsToAdmin(String userId) {
        return getRequestsByAuthor(userId, false).stream()
                .filter(request -> request.getReviewer() != null && request.getReviewer().equals(userId))
                .sorted((r1, r2) -> r2.getDateCreated().compareTo(r1.getDateCreated()))
                .toList();
    }

    public List<CitizenRequestInfo> getUnassignedRequestsToHead(String userId) {
        return getRequestsByAuthor(userId, false).stream()
                .filter(request -> request.getTimePassed() != null && request.getTimePassed())
                .filter(request -> request.getReviewer() == null)
                .sorted((r1, r2) -> r2.getDateCreated().compareTo(r1.getDateCreated()))
                .toList();
    }

    public List<CitizenRequestInfo> getAssignedRequestsToHead(String userId) {
        return getRequestsByAuthor(userId, false).stream()
                .filter(request -> request.getPastDeadline() == null || !request.getPastDeadline())
                .sorted((r1, r2) -> r2.getDateCreated().compareTo(r1.getDateCreated()))
                .toList();
    }

    public CitizenRequestInfo getRequest(String userId, Long electionId) {
        return getRequestByAuthorAndElection(userId, electionId);
    }

    public void sendRequest(CitizenRequestInfo citizenRequestInfo) {
        camundaEngineService.startProcessInstance(processKey, citizenRequestInfo.toVariables());
    }

    public void sendRequestCorrection(String userId, Long electionId, CitizenRequestInfo citizenRequestInfo) {
        List<Task> tasks = camundaEngineService.getUserTasks(userId, processKey);
        for (Task task : tasks) {
            Map<String, Object> variables = camundaEngineService.getTaskVariables(task.getId());
            if (variables.get("ElectionId") != null && variables.get("ElectionId") == electionId) {
                citizenRequestInfo.setDateCreated((String) variables.get("DateCreated"));
                citizenRequestInfo.setDeadline((String) variables.get("Deadline"));
                citizenRequestInfo.setReviewer((String) variables.get("Reviewer"));
                camundaEngineService.completeTask(task.getId(), citizenRequestInfo.toVariables());
                return;
            }
        }
    }

    public void takeOverRequest(String userId, String requestAuthor, Long requestElectionId) {
        HistoricProcessInstance instance = getInstanceByAuthorAndElection(requestAuthor, requestElectionId);
        if (instance != null) {
            camundaEngineService.sendMessage("TakeRequestMessage", instance.getId(), Map.of("Reviewer", userId));
            List<Task> tasks = camundaEngineService.getUnassignedGroupTasks(userId, processKey);
            Task request = getTaskByAuthorAndElection(tasks, requestAuthor, requestElectionId);
            if (request != null) {
                camundaEngineService.claimTask(request.getId(), userId);
            }
        }
    }

    public void sendRequestReview(String userId, String requestAuthor, Long requestElectionId, RequestReviewInfo reviewInfo) {
        List<Task> tasks = camundaEngineService.getUserTasks(userId, processKey);
        Task task = getTaskByAuthorAndElection(tasks, requestAuthor, requestElectionId);
        if (task != null) {
            camundaEngineService.completeTask(task.getId(), reviewInfo.toVariables());
        }
    }

    public void assignReviewerToRequest(String userId, String requestAuthor, Long requestElectionId, String reviewer) {
        List<Task> tasks = camundaEngineService.getUnassignedGroupTasks("head", processKey);
        Task request = getTaskByAuthorAndElection(tasks, requestAuthor, requestElectionId);
        if (request != null) {
            camundaEngineService.claimTask(request.getId(), userId);
            camundaEngineService.completeTask(request.getId(), Map.of("Reviewer", reviewer));
        }
    }

    private List<CitizenRequestInfo> getRequestsByAuthor(String author, boolean includeAuthor) {
        List<CitizenRequestInfo> citizenRequests = new ArrayList<>();
        List<HistoricProcessInstance> instances = camundaEngineService.getProcessInstances(processKey);
        for (HistoricProcessInstance instance : instances) {
            List<HistoricVariableInstance> variables = camundaEngineService.getProcessInstanceVariables(instance.getId());
            boolean isAuthor = variables.stream().anyMatch(variable -> variable.getName().equals("Author") && variable.getValue().equals(author));
            if (includeAuthor == isAuthor) {
                Map<String, Object> values = new HashMap<>();
                for (HistoricVariableInstance variable : variables) {
                    values.put(variable.getName(), variable.getValue());
                }
                citizenRequests.add(new CitizenRequestInfo(values));
            }
        }
        return citizenRequests;
    }

    private CitizenRequestInfo getRequestByAuthorAndElection(String userId, Long electionId) {
        List<HistoricProcessInstance> instances = camundaEngineService.getProcessInstances(processKey);
        for (HistoricProcessInstance instance : instances) {
            List<HistoricVariableInstance> variables = camundaEngineService.getProcessInstanceVariables(instance.getId());
            if (isRequestAuthorAndElection(variables, userId, electionId)) {
                Map<String, Object> values = new HashMap<>();
                for (HistoricVariableInstance variable : variables) {
                    values.put(variable.getName(), variable.getValue());
                }
                return new CitizenRequestInfo(values);
            }
        }
        return new CitizenRequestInfo();
    }

    private HistoricProcessInstance getInstanceByAuthorAndElection(String userId, Long electionId) {
        List<HistoricProcessInstance> instances = camundaEngineService.getProcessInstances(processKey);
        for (HistoricProcessInstance instance : instances) {
            List<HistoricVariableInstance> variables = camundaEngineService.getProcessInstanceVariables(instance.getId());
            if (isRequestAuthorAndElection(variables, userId, electionId)) {
                return instance;
            }
        }
        return null;
    }

    private Task getTaskByAuthorAndElection(List<Task> tasks, String requestAuthor, Long requestElectionId) {
        for (Task task : tasks) {
            Map<String, Object> variables = camundaEngineService.getTaskVariables(task.getId());
            if (isRequestAuthorAndElection(variables, requestAuthor, requestElectionId)) {
                return task;
            }
        }
        return null;
    }

    private boolean isRequestAuthorAndElection(List<HistoricVariableInstance> variables, String requestAuthor, Long requestElectionId) {
        boolean isRequestAuthor = variables.stream().anyMatch(variable -> variable.getName().equals("Author") && variable.getValue().equals(requestAuthor));
        boolean isRequestElection = variables.stream().anyMatch(variable -> variable.getName().equals("ElectionId") && variable.getValue().equals(requestElectionId));
        return isRequestAuthor && isRequestElection;
    }

    private boolean isRequestAuthorAndElection(Map<String, Object> variables, String requestAuthor, Long requestElectionId) {
        boolean isRequestAuthor = variables.get("Author") != null && requestAuthor.equals(variables.get("Author"));
        boolean isRequestElection = variables.get("ElectionId") != null && requestElectionId.equals(variables.get("ElectionId"));
        return isRequestAuthor && isRequestElection;
    }
}
