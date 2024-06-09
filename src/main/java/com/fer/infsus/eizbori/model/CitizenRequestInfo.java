package com.fer.infsus.eizbori.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String dob;
    private String address;
    private String oib;
    private String idn;
    private String dateCreated;
    private String deadline;
    private String status;

    public CitizenRequestInfo(Map<String, Object> variables) {
        this.electionId = (Long) variables.get("ElectionId");
        this.author = (String) variables.get("Author");
        this.firstName = (String) variables.get("FirstName");
        this.lastName = (String) variables.get("LastName");
        this.dob = (String) variables.get("DOB");
        this.address = (String) variables.get("Address");
        this.oib = (String) variables.get("OIB");
        this.idn = (String) variables.get("IDN");
        this.dateCreated = (String) variables.get("DateCreated");
        this.deadline = (String) variables.get("Deadline");
        this.status = (String) variables.get("Status");
    }

    public boolean isEmpty() {
        return this.electionId == null
                && this.author == null
                && this.firstName == null
                && this.lastName == null
                && this.dob == null
                && this.address == null
                && this.oib == null
                && this.idn == null
                && this.dateCreated == null
                && this.deadline == null
                && this.status == null;
    }

    public Map<String, Object> toVariables() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("ElectionId", this.electionId);
        variables.put("Author", this.author);
        variables.put("FirstName", this.firstName);
        variables.put("LastName", this.lastName);
        variables.put("DOB", this.dob);
        variables.put("Address", this.address);
        variables.put("OIB", this.oib);
        variables.put("IDN", this.idn);
        variables.put("DateCreated", this.dateCreated);
        variables.put("Deadline", this.deadline);
        variables.put("Status", this.status);
        return variables;
    }
}
