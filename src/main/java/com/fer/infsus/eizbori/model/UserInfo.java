package com.fer.infsus.eizbori.model;

import lombok.Getter;
import org.camunda.bpm.engine.identity.User;

@Getter
public class UserInfo {
    private final String userId;
    private final String fullName;

    public UserInfo(User user) {
        this.userId = user.getId();
        this.fullName = user.getFirstName() + " " + user.getLastName();
    }
}
