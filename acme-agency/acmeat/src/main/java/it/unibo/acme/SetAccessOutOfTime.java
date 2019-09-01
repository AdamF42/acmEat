package it.unibo.acme;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static it.unibo.utils.AcmeVariables.OUT_OF_TIME;


public class SetAccessOutOfTime implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable(OUT_OF_TIME, OUT_OF_TIME);
    }
}
