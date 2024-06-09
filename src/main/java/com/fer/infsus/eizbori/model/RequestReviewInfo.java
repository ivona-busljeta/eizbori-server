package com.fer.infsus.eizbori.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class RequestReviewInfo {
    private Boolean passedReview;
    private String comment;

    public Map<String, Object> toVariables() {
        return Map.of("PassedReview", passedReview, "Comment", comment);
    }
}
