package com.fer.infsus.eizbori.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
public class CitizenRequestInfo {
    private Long electionId;
    private String electionName;
    private String author;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String dobStr;
    private String address;
    private String oib;
    private String idn;
    private LocalDate dateCreated;
    private Boolean timePassed;
    private LocalDate deadline;
    private Boolean pastDeadline;
    private String status;
    private String reviewer;
    private Boolean passedReview;
    private String comment;

    public void setDob(String dob) {
        this.dob = LocalDate.parse(dob);
        this.dobStr = dob;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = LocalDate.parse(dateCreated);
    }

    public void setDeadline(String deadline) {
        this.deadline = LocalDate.parse(deadline);
    }

    public CitizenRequestInfo(Map<String, Object> variables) {
        this.electionId = (Long) variables.get("ElectionId");
        this.author = (String) variables.get("Author");
        this.firstName = (String) variables.get("FirstName");
        this.lastName = (String) variables.get("LastName");
        this.dob = LocalDate.parse((String) variables.get("DOB"));
        this.dobStr = (String) variables.get("DOB");
        this.address = (String) variables.get("Address");
        this.oib = (String) variables.get("OIB");
        this.idn = (String) variables.get("IDN");
        this.dateCreated = LocalDate.parse((String) variables.get("DateCreated"));
        this.timePassed = (Boolean) variables.get("TimePassed");
        this.deadline = LocalDate.parse((String) variables.get("Deadline"));
        this.pastDeadline = (Boolean) variables.get("PastDeadline");
        this.status = (String) variables.get("Status");
        this.reviewer = (String) variables.get("Reviewer");
        this.passedReview = (Boolean) variables.get("PassedReview");
        this.comment = (String) variables.get("Comment");
    }

    public boolean isEmpty() {
        return this.electionId == null
                && this.author == null
                && this.firstName == null
                && this.lastName == null
                && this.dob == null
                && this.dobStr == null
                && this.address == null
                && this.oib == null
                && this.idn == null
                && this.dateCreated == null
                && this.timePassed == null
                && this.deadline == null
                && this.pastDeadline == null
                && this.status == null
                && this.reviewer == null
                && this.passedReview == null
                && this.comment == null;
    }

    public Map<String, Object> toVariables() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("ElectionId", this.electionId);
        variables.put("Author", this.author);
        variables.put("FirstName", this.firstName);
        variables.put("LastName", this.lastName);
        variables.put("DOB", this.dob.toString());
        variables.put("Address", this.address);
        variables.put("OIB", this.oib);
        variables.put("IDN", this.idn);
        variables.put("DateCreated", this.dateCreated.toString());
        variables.put("TimePassed", this.timePassed);
        variables.put("Deadline", this.deadline.toString());
        variables.put("PastDeadline", this.pastDeadline);
        variables.put("Status", this.status);
        variables.put("Reviewer", this.reviewer);
        variables.put("PassedReview", this.passedReview);
        variables.put("Comment", this.comment);
        return variables;
    }
}
