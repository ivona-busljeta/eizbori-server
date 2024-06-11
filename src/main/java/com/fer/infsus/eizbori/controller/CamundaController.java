package com.fer.infsus.eizbori.controller;

import com.fer.infsus.eizbori.model.*;
import com.fer.infsus.eizbori.service.CamundaService;
import com.fer.infsus.eizbori.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class CamundaController {

    private final String GROUP_CITIZEN = "citizen";
    private final String GROUP_ADMIN = "admin";
    private final String GROUP_HEAD = "head";

    private final CamundaService camundaService;
    private final ElectionService electionService;


    @Autowired
    public CamundaController(ElectionService electionService, CamundaService camundaService) {
        this.electionService = electionService;
        this.camundaService = camundaService;
    }

    @GetMapping("/{userId}/elections")
    public String elections(@PathVariable String userId, Model model) {
        if (isCitizen(userId)) {
            List<ElectionInfo> elections = electionService.getActiveElections().stream().map(ElectionInfo::new).toList();
            List<CitizenRequestInfo> requests = camundaService.getRequestsToCitizen(userId);
            requests.forEach(request -> {
                Optional<ElectionInfo> electionInfo = elections.stream()
                        .filter(election -> Objects.equals(request.getElectionId(), election.getId()))
                        .findFirst();
                if (electionInfo.isPresent()) {
                    electionInfo.get().setCanApply(false);
                    request.setElectionName(electionInfo.get().getName());
                }
            });

            fillInUserGroups(userId, model);
            model.addAttribute("elections", elections);
            model.addAttribute("requests", requests);

            return "elections";
        }
        return "403";
    }

    @GetMapping("/{userId}/elections/{electionId}/application")
    public String electionApplication(@PathVariable String userId, @PathVariable Long electionId, Model model) {
        if (isCitizen(userId)) {
            ElectionInfo election = new ElectionInfo(electionService.getElection(electionId));

            fillInUserGroups(userId, model);
            model.addAttribute("title", "Online voting application for " + election.getName());
            model.addAttribute("request", new CitizenRequestInfo());
            model.addAttribute("passedReview", true);

            return "application";
        }
        return "403";
    }

    @GetMapping("/{userId}/elections/{electionId}/application/edit")
    public String editApplication(@PathVariable String userId, @PathVariable Long electionId, Model model) {
        if (isCitizen(userId)) {
            ElectionInfo election = new ElectionInfo(electionService.getElection(electionId));
            CitizenRequestInfo request = camundaService.getRequest(userId, electionId);

            fillInUserGroups(userId, model);
            model.addAttribute("title", "Correction of application for " + election.getName());
            model.addAttribute("request", request);
            model.addAttribute("passedReview", request.getPassedReview());

            return "application";
        }
        return "403";
    }

    @PostMapping("/{userId}/elections/{electionId}/application/submit")
    public String submitApplication(@PathVariable String userId, @PathVariable Long electionId, CitizenRequestInfo citizenRequestInfo) {
        if (isCitizen(userId)) {
            CitizenRequestInfo request = camundaService.getRequest(userId, electionId);
            if (request.isEmpty()) {
                ElectionInfo election = new ElectionInfo(electionService.getElection(electionId));
                citizenRequestInfo.setElectionId(electionId);
                citizenRequestInfo.setAuthor(userId);
                citizenRequestInfo.setDateCreated(LocalDate.now().toString());
                citizenRequestInfo.setDeadline(election.getDeadline().toString());
                citizenRequestInfo.setStatus("Created");

                camundaService.sendRequest(citizenRequestInfo);
                return "redirect:/{userId}/elections";

            } else if (request.getStatus().equals("Returned")) {
                citizenRequestInfo.setAuthor(userId);
                camundaService.sendRequestCorrection(userId, electionId, citizenRequestInfo);
                return "redirect:/{userId}/elections";
            }
        }
        return "403";
    }

    @GetMapping("/{userId}/requests")
    public String requests(@PathVariable String userId, Model model) {
        if (isAdmin(userId)) {
            List<CitizenRequestInfo> unassigned = camundaService.getUnassignedRequestsToAdmin(userId);
            List<CitizenRequestInfo> accepted = camundaService.getAssignedRequestsToAdmin(userId);

            fillInUserGroups(userId, model);
            model.addAttribute("unassigned", unassigned);
            model.addAttribute("accepted", accepted);

            return "requests";
        }
        return "403";
    }

    @PostMapping("/{userId}/requests/take-over")
    public String takeOverRequest(@PathVariable String userId, @RequestParam String author, @RequestParam Long electionId) {
        if (isAdmin(userId)) {
            camundaService.takeOverRequest(userId, author, electionId);
            return "redirect:/{userId}/requests";
        }
        return "403";
    }

    @GetMapping("/{userId}/requests/review")
    public String requestReview(@PathVariable String userId, @RequestParam String author, @RequestParam Long electionId, Model model) {
        if (isAdmin(userId)) {
            CitizenRequestInfo request = camundaService.getRequest(author, electionId);

            fillInUserGroups(userId, model);
            model.addAttribute("request", request);
            model.addAttribute("review", new RequestReviewInfo());

            return "review";
        }
        return "403";
    }

    @PostMapping("/{userId}/requests/review/submit")
    public String submitReview(@PathVariable String userId, @RequestParam String author, @RequestParam Long electionId, RequestReviewInfo requestReviewInfo) {
        if (isAdmin(userId)) {
            camundaService.sendRequestReview(userId, author, electionId, requestReviewInfo);
            return "redirect:/{userId}/requests";
        }
        return "403";
    }

    @GetMapping("/{userId}/request-management")
    public String requestManagement(@PathVariable String userId, Model model) {
        if (isHead(userId)) {
            List<CitizenRequestInfo> unassigned = camundaService.getUnassignedRequestsToHead(userId);
            List<CitizenRequestInfo> assigned = camundaService.getAssignedRequestsToHead(userId);

            fillInUserGroups(userId, model);
            model.addAttribute("unassigned", unassigned);
            model.addAttribute("assigned", assigned);

            return "management";
        }
        return "403";
    }

    @GetMapping("/{userId}/request-management/assign-reviewer")
    public String assignReviewer(@PathVariable String userId, @RequestParam String author, @RequestParam Long electionId, Model model) {
        if (isHead(userId)) {
            List<UserInfo> reviewers = camundaService.getUsersInGroup(GROUP_ADMIN).stream()
                    .filter(user -> !user.getId().equals(author))
                    .map(UserInfo::new)
                    .toList();

            fillInUserGroups(userId, model);
            model.addAttribute("author", author);
            model.addAttribute("electionId", electionId);
            model.addAttribute("reviewers", reviewers);
            model.addAttribute("assignedReviewer", new UserInfo());

            return "assignment";
        }
        return "403";
    }

    @PostMapping("/{userId}/request-management/assign-reviewer/submit")
    public String submitAssignedReviewer(@PathVariable String userId, @RequestParam String author, @RequestParam Long electionId, UserInfo userInfo) {
        if (isHead(userId)) {
            camundaService.assignReviewerToRequest(userId, author, electionId, userInfo.getUserId());
            return "redirect:/{userId}/request-management";
        }
        return "403";
    }
    
    private boolean isCitizen(String userId) {
        return camundaService.isUserInGroup(userId, GROUP_CITIZEN);
    }
    
    private boolean isAdmin(String userId) {
        return camundaService.isUserInGroup(userId, GROUP_ADMIN);
    }
    
    private boolean isHead(String userId) {
        return camundaService.isUserInGroup(userId, GROUP_HEAD);
    }

    private void fillInUserGroups(String userId, Model model) {
        model.addAttribute(GROUP_CITIZEN, isCitizen(userId));
        model.addAttribute(GROUP_ADMIN, isAdmin(userId));
        model.addAttribute(GROUP_HEAD, isHead(userId));
    }
}
