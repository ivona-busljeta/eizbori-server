package com.fer.infsus.eizbori.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CheckDeadline implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        LocalDate dateCreated = LocalDate.parse((String) delegateExecution.getVariable("DateCreated"));
        LocalDate deadline = LocalDate.parse((String) delegateExecution.getVariable("Deadline"));
        delegateExecution.setVariable("PastDeadline", dateCreated.isAfter(deadline));
    }
}
