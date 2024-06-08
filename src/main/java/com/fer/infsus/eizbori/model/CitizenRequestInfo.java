package com.fer.infsus.eizbori.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class CitizenRequestInfo {
    private String requestId;
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
        this.requestId = (String) variables.get("RequestId");
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
}
