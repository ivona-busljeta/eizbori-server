package com.fer.infsus.eizbori.service;

import com.fer.infsus.eizbori.model.CitizenRequestDetailedInfo;
import com.fer.infsus.eizbori.model.CitizenRequestInfo;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CamundaService {

    private final String processKey = "ProcessRequest";
    private final String message = "TakeRequestMessage";

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

    public void sendCitizenRequest(CitizenRequestInfo citizenRequestInfo) {
        camundaEngineService.startProcessInstance(processKey, citizenRequestInfo.toVariables());
    }

    public List<CitizenRequestInfo> getCitizenRequests(String userId) {
        List<CitizenRequestInfo> citizenRequests = new ArrayList<>();
        List<HistoricProcessInstance> instances = camundaEngineService.getProcessInstances(processKey);
        for (HistoricProcessInstance instance : instances) {
            List<HistoricVariableInstance> variables = camundaEngineService.getProcessInstanceVariables(instance.getId());
            boolean isAuthor = variables.stream().anyMatch(variable -> variable.getName().equals("Author") && variable.getValue().equals(userId));
            if (isAuthor) {
                Map<String, Object> values = variables.stream().collect(Collectors.toMap(HistoricVariableInstance::getName, HistoricVariableInstance::getValue));
                citizenRequests.add(new CitizenRequestInfo(values));
            }
        }
        return citizenRequests;
    }

    public List<CitizenRequestInfo> getCitizenRequestsToAdmin(String userId) {
        List<CitizenRequestInfo> citizenRequests = new ArrayList<>();
        List<HistoricProcessInstance> instances = camundaEngineService.getProcessInstances(processKey);
        for (HistoricProcessInstance instance : instances) {
            List<HistoricVariableInstance> variables = camundaEngineService.getProcessInstanceVariables(instance.getId());
            boolean isAuthor = variables.stream().anyMatch(variable -> variable.getName().equals("Author") && variable.getValue().equals(userId));
            if (!isAuthor) {
                Map<String, Object> values = variables.stream().collect(Collectors.toMap(HistoricVariableInstance::getName, HistoricVariableInstance::getValue));

                boolean isTimePassed = values.get("TimePassed") != null && (boolean) values.get("TimePassed");
                boolean isReviewerSet = values.get("Reviewer") != null;
                if (!isTimePassed && !isReviewerSet) {
                    citizenRequests.add(new CitizenRequestInfo(values));
                }
            }
        }
        return citizenRequests;
    }

    public List<CitizenRequestInfo> getYourAcceptedCitizenRequests(String userId) {
        List<CitizenRequestInfo> citizenRequests = new ArrayList<>();
        List<HistoricProcessInstance> instances = camundaEngineService.getProcessInstances(processKey);
        for (HistoricProcessInstance instance : instances) {
            List<HistoricVariableInstance> variables = camundaEngineService.getProcessInstanceVariables(instance.getId());
            boolean isAuthor = variables.stream().anyMatch(variable -> variable.getName().equals("Author") && variable.getValue().equals(userId));
            if (!isAuthor) {
                Map<String, Object> values = variables.stream().collect(Collectors.toMap(HistoricVariableInstance::getName, HistoricVariableInstance::getValue));

                boolean youAreReviewer = values.get("Reviewer") != null && userId.equals(values.get("Reviewer"));
                if (youAreReviewer) {
                    citizenRequests.add(new CitizenRequestInfo(values));
                }
            }
        }
        return citizenRequests;
    }

    public List<CitizenRequestDetailedInfo> getCitizenRequestsToMaster(String userId) {
        List<CitizenRequestDetailedInfo> citizenRequests = new ArrayList<>();
        List<HistoricProcessInstance> instances = camundaEngineService.getProcessInstances(processKey);
        for (HistoricProcessInstance instance : instances) {
            List<HistoricVariableInstance> variables = camundaEngineService.getProcessInstanceVariables(instance.getId());
            boolean isAuthor = variables.stream().anyMatch(variable -> variable.getName().equals("Author") && variable.getValue().equals(userId));
            if (!isAuthor) {
                Map<String, Object> values = variables.stream().collect(Collectors.toMap(HistoricVariableInstance::getName, HistoricVariableInstance::getValue));

                boolean isReviewerSet = values.get("Reviewer") != null;
                if (isReviewerSet) {
                    citizenRequests.add(new CitizenRequestDetailedInfo(values));
                }
            }
        }
        return citizenRequests;
    }

    public List<CitizenRequestDetailedInfo> getUnassignedCitizenRequestsToMaster(String userId) {
        List<CitizenRequestDetailedInfo> citizenRequests = new ArrayList<>();
        List<HistoricProcessInstance> instances = camundaEngineService.getProcessInstances(processKey);
        for (HistoricProcessInstance instance : instances) {
            List<HistoricVariableInstance> variables = camundaEngineService.getProcessInstanceVariables(instance.getId());
            boolean isAuthor = variables.stream().anyMatch(variable -> variable.getName().equals("Author") && variable.getValue().equals(userId));
            if (!isAuthor) {
                Map<String, Object> values = variables.stream().collect(Collectors.toMap(HistoricVariableInstance::getName, HistoricVariableInstance::getValue));

                boolean isTimePassed = values.get("TimePassed") != null && (boolean) values.get("TimePassed");
                boolean isReviewerSet = values.get("Reviewer") != null;
                if (isTimePassed && !isReviewerSet) {
                    citizenRequests.add(new CitizenRequestDetailedInfo(values));
                }
            }
        }
        return citizenRequests;
    }

    public CitizenRequestInfo getCitizenRequestForElection(String userId, Long electionId) {
        List<HistoricProcessInstance> instances = camundaEngineService.getProcessInstances(processKey);
        for (HistoricProcessInstance instance : instances) {
            List<HistoricVariableInstance> variables = camundaEngineService.getProcessInstanceVariables(instance.getId());
            boolean isAuthor = variables.stream().anyMatch(variable -> variable.getName().equals("Author") && variable.getValue().equals(userId));
            boolean isForElection = variables.stream().anyMatch(variable -> variable.getName().equals("ElectionId") && variable.getValue().equals(electionId));
            if (isAuthor && isForElection) {
                Map<String, Object> values = variables.stream().collect(Collectors.toMap(HistoricVariableInstance::getName, HistoricVariableInstance::getValue));
                return new CitizenRequestInfo(values);
            }
        }
        return new CitizenRequestInfo();
    }

    public void sendCitizenRequestCorrection(String userId, Long electionId, CitizenRequestInfo citizenRequestInfo) {
        List<Task> tasks = camundaEngineService.getUserTasks(userId, processKey);
        for (Task task : tasks) {
            Map<String, Object> variables = camundaEngineService.getTaskVariables(task.getId());
            if (variables.get("ElectionId") != null && variables.get("ElectionId") == electionId) {
                citizenRequestInfo.setDateCreated(variables.get("DateCreated").toString());
                citizenRequestInfo.setDeadline(variables.get("Deadline").toString());
                camundaEngineService.completeTask(task.getId(), citizenRequestInfo.toVariables());
                return;
            }
        }
    }
}
