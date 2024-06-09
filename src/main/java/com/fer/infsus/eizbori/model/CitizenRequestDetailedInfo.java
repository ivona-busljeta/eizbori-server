package com.fer.infsus.eizbori.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class CitizenRequestDetailedInfo extends CitizenRequestInfo {
    private final String reviewer;

    public CitizenRequestDetailedInfo(Map<String, Object> variables) {
        super(variables);
        this.reviewer = (String) variables.get("Reviewer");
    }
}
