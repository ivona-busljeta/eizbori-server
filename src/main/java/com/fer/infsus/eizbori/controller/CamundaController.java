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
        if (camundaService.isUserInGroup(userId, GROUP_CITIZEN)) {
            List<ElectionInfo> elections = electionService.getActiveElections().stream().map(ElectionInfo::new).toList();
            List<CitizenRequestInfo> requests = camundaService.getCitizenRequests(userId);
            requests.forEach(request -> {
                Optional<ElectionInfo> electionInfo = elections.stream()
                        .filter(election -> Objects.equals(request.getElectionId(), election.getId()))
                        .findFirst();
                if (electionInfo.isPresent()) {
                    electionInfo.get().setCanApply(false);
                    request.setElectionName(electionInfo.get().getName());
                }
            });

            boolean isUserInCitizenGroup = camundaService.isUserInGroup(userId, GROUP_CITIZEN);
            boolean isUserInAdminGroup = camundaService.isUserInGroup(userId, GROUP_ADMIN);
            boolean isUserInMasterGroup = camundaService.isUserInGroup(userId, GROUP_HEAD);

            model.addAttribute("elections", elections);
            model.addAttribute("requests", requests);
            model.addAttribute("isUserInCitizenGroup", isUserInCitizenGroup);
            model.addAttribute("isUserInAdminGroup", isUserInAdminGroup);
            model.addAttribute("isUserInMasterGroup", isUserInMasterGroup);

            return "elections";
        }
        return "403";
    }

    @GetMapping("/{userId}/elections/{electionId}/application")
    public String electionApplication(@PathVariable String userId, @PathVariable Long electionId, Model model) {
        if (camundaService.isUserInGroup(userId, GROUP_CITIZEN)) {
            ElectionInfo election = new ElectionInfo(electionService.getElection(electionId));
            model.addAttribute("electionName", election.getName());
            model.addAttribute("formData", new CitizenRequestInfo());

            return "applyForm";
        }
        return "403";
    }

    @GetMapping("/{userId}/elections/{electionId}/application/edit")
    public String editApplication(@PathVariable String userId, @PathVariable Long electionId, Model model) {
        if (camundaService.isUserInGroup(userId, GROUP_CITIZEN)) {
            CitizenRequestInfo request = camundaService.getCitizenRequestForElection(userId, electionId);
            model.addAttribute("electionName", request.getElectionName());
            model.addAttribute("formData", request);

            return "applyForm";
        }
        return "403";
    }

    @PostMapping("/{userId}/elections/{electionId}/application/submit")
    public String submitApplication(@PathVariable String userId, @PathVariable Long electionId, CitizenRequestInfo citizenRequestInfo) {
        if (camundaService.isUserInGroup(userId, GROUP_CITIZEN)) {
            CitizenRequestInfo request = camundaService.getCitizenRequestForElection(userId, electionId);
            if (request.isEmpty()) {
                citizenRequestInfo.setElectionId(electionId);
                citizenRequestInfo.setAuthor(userId);
                citizenRequestInfo.setDateCreated(LocalDate.now().toString());
                citizenRequestInfo.setDeadline(LocalDate.now().toString());
                citizenRequestInfo.setStatus("Created");

                camundaService.sendCitizenRequest(citizenRequestInfo);
                return "redirect:/{userId}/elections";

            } else if (request.getStatus().equals("Returned")) {
                citizenRequestInfo.setAuthor(userId);
                camundaService.sendCitizenRequestCorrection(userId, electionId, citizenRequestInfo);
                return "redirect:/{userId}/elections";
            }
        }
        return "403";
    }

    @GetMapping("/{userId}/requests")
    public String requests(@PathVariable String userId, Model model) {
        if (camundaService.isUserInGroup(userId, GROUP_ADMIN)) {
            List<CitizenRequestInfo> requests = camundaService.getCitizenRequestsToAdmin(userId);
            List<CitizenRequestInfo> acceptedRequests = camundaService.getYourAcceptedCitizenRequests(userId);

            boolean isUserInCitizenGroup = camundaService.isUserInGroup(userId, GROUP_CITIZEN);
            boolean isUserInAdminGroup = camundaService.isUserInGroup(userId, GROUP_ADMIN);
            boolean isUserInMasterGroup = camundaService.isUserInGroup(userId, GROUP_HEAD);

            model.addAttribute("requests", requests);
            model.addAttribute("acceptedRequests", acceptedRequests);
            model.addAttribute("isUserInCitizenGroup", isUserInCitizenGroup);
            model.addAttribute("isUserInAdminGroup", isUserInAdminGroup);
            model.addAttribute("isUserInMasterGroup", isUserInMasterGroup);

            return "requests";
        }
        return "403";
    }

    @GetMapping("/{userId}/requests/review")
    public String requestReview(@PathVariable String userId, @RequestParam String author, @RequestParam Long electionId, Model model) {
        if (camundaService.isUserInGroup(userId, GROUP_ADMIN)) {
            CitizenRequestInfo request = camundaService.getCitizenRequestForElection(author, electionId);
            model.addAttribute("request", request);
            model.addAttribute("review", new RequestReviewInfo());
            return "reviewRequest";
        }
        return "403";
    }

    @PostMapping("/{userId}/requests/review/submit")
    public String submitReview(@PathVariable String userId, @RequestParam String author, @RequestParam Long electionId, RequestReviewInfo requestReviewInfo) {
        if (camundaService.isUserInGroup(userId, GROUP_ADMIN)) {
            camundaService.sendRequestReview(userId, author, electionId, requestReviewInfo);
            return "redirect:/{userId}/requests";
        }
        return "403";
    }

    @GetMapping("/{userId}/request-control")
    public String master(@PathVariable String userId, Model model) {
        if (camundaService.isUserInGroup(userId, GROUP_HEAD)) {
            List<CitizenRequestDetailedInfo> unassigned = camundaService.getUnassignedCitizenRequestsToMaster(userId);
            List<CitizenRequestDetailedInfo> assigned = camundaService.getCitizenAssignedRequestsToMaster(userId);

            boolean isUserInCitizenGroup = camundaService.isUserInGroup(userId, GROUP_CITIZEN);
            boolean isUserInAdminGroup = camundaService.isUserInGroup(userId, GROUP_ADMIN);
            boolean isUserInMasterGroup = camundaService.isUserInGroup(userId, GROUP_HEAD);

            model.addAttribute("unassigned", unassigned);
            model.addAttribute("assigned", assigned);
            model.addAttribute("isUserInCitizenGroup", isUserInCitizenGroup);
            model.addAttribute("isUserInAdminGroup", isUserInAdminGroup);
            model.addAttribute("isUserInMasterGroup", isUserInMasterGroup);

            return "master";
        }
        return "403";
    }

    @GetMapping("/{userId}/request-control/assign-reviewer")
    public String assignReviewer(@PathVariable String userId, @RequestParam String author, @RequestParam Long electionId, Model model) {
        if (camundaService.isUserInGroup(userId, GROUP_HEAD)) {
            List<UserInfo> reviewers = camundaService.getUsersInGroup(GROUP_ADMIN).stream().map(UserInfo::new).toList();

            model.addAttribute("author", author);
            model.addAttribute("electionId", electionId);
            model.addAttribute("reviewers", reviewers);
            model.addAttribute("assignedReviewer", new UserInfo());

            return "assignReviewer";
        }
        return "403";
    }

    @PostMapping("/{userId}/request-control/assign-reviewer/submit")
    public String submitAssignedReviewer(@PathVariable String userId, @RequestParam String author, @RequestParam Long electionId, UserInfo userInfo) {
        if (camundaService.isUserInGroup(userId, GROUP_HEAD)) {
            camundaService.assignReviewerToRequest(userId, author, electionId, userInfo.getUserId());
            return "redirect:/{userId}/request-control";
        }
        return "403";
    }

}
