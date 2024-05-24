package com.fer.infsus.eizbori.dto;

import com.fer.infsus.eizbori.entity.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
public class UpdateElectionDTO {

    @Getter
    private long id;

    @NotNull
    private String status;

    public Status getStatus() {
        return Status.valueOf(status);
    }
}
