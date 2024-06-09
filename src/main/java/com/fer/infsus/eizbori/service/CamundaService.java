package com.fer.infsus.eizbori.service;

import com.fer.infsus.eizbori.model.CitizenRequestInfo;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
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

                boolean isTimePassed = values.containsKey("TimePassed") && (boolean) values.get("TimePassed");
                if (!isTimePassed) {
                    citizenRequests.add(new CitizenRequestInfo(values));
                }
            }
        }
        return citizenRequests;
    }

    public List<CitizenRequestInfo> getCitizenTimeoutRequestsToMaster(String userId) {
        List<CitizenRequestInfo> citizenRequests = new ArrayList<>();
        List<HistoricProcessInstance> instances = camundaEngineService.getProcessInstances(processKey);
        for (HistoricProcessInstance instance : instances) {
            List<HistoricVariableInstance> variables = camundaEngineService.getProcessInstanceVariables(instance.getId());
            boolean isAuthor = variables.stream().anyMatch(variable -> variable.getName().equals("Author") && variable.getValue().equals(userId));
            if (!isAuthor) {
                Map<String, Object> values = variables.stream().collect(Collectors.toMap(HistoricVariableInstance::getName, HistoricVariableInstance::getValue));

                boolean isTimePassed = values.containsKey("TimePassed") && (boolean) values.get("TimePassed");
                if (isTimePassed) {
                    citizenRequests.add(new CitizenRequestInfo(values));
                }
            }
        }
        return citizenRequests;
    }
}
