package com.fer.infsus.eizbori.controller;

import com.fer.infsus.eizbori.model.CitizenRequestInfo;
import com.fer.infsus.eizbori.model.ElectionInfo;
import com.fer.infsus.eizbori.service.CamundaService;
import com.fer.infsus.eizbori.service.ElectionService;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CamundaController {

    private final CamundaService camundaService;
    private final ElectionService electionService;
    private final IdentityService identityService;

    @Autowired
    public CamundaController(ElectionService electionService, CamundaService camundaService, IdentityService identityService) {
        this.electionService = electionService;
        this.camundaService = camundaService;
        this.identityService = identityService;
    }

    @GetMapping("/{userId}/elections")
    public String elections(@PathVariable String userId, Model model) {
        if (isUserInGroup(userId, "citizen")) {
            List<CitizenRequestInfo> requests = camundaService.getCitizenRequests(userId);
            List<ElectionInfo> elections = electionService.getActiveElections();

            List<Long> appliedElections = requests.stream().map(CitizenRequestInfo::getElectionId).toList();
            elections.forEach(election -> {
                if (appliedElections.contains(election.getId())) {
                    election.setCanApply(false);
                }
            });

            model.addAttribute("elections", elections);
            model.addAttribute("requests", requests);

            return "elections";
        }
        return "403";
    }

    private boolean isUserInGroup(String userId, String groupId) {
        return !identityService
                .createUserQuery()
                .userId(userId)
                .memberOfGroup(groupId)
                .list()
                .isEmpty();
    }
}
