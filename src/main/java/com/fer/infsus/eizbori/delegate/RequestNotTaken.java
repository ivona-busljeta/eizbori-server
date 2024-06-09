package com.fer.infsus.eizbori.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class RequestNotTaken implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {delegateExecution.setVariable("TimePassed", true);
    }
}
