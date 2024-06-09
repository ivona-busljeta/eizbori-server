package com.fer.infsus.eizbori.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.camunda.bpm.engine.identity.User;

@Getter
@Setter
@NoArgsConstructor
public class UserInfo {
    private String userId;
    private String fullName;

    public UserInfo(User user) {
        this.userId = user.getId();
        this.fullName = user.getFirstName() + " " + user.getLastName();
    }
}
