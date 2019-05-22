package it.unibo.soseng.bank;

import it.unibo.bpmn2jolie.generated.Bank;
import it.unibo.bpmn2jolie.generated.BankService;
import it.unibo.bpmn2jolie.generated.GetTokenResponse;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GetToken implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        Bank bankService = new BankService().getBankServicePort();


        it.unibo.bpmn2jolie.generated.GetToken payment = new it.unibo.bpmn2jolie.generated.GetToken();
        payment.setAmount((double) 10);
        payment.setName("Test");

        GetTokenResponse response = bankService.getToken(payment);

        execution.setVariable("bankToken", response.getSid());


        System.out.println("\n\n  ... GetToken invoked by "
                + "processDefinitionId=" + execution.getProcessDefinitionId()
                + ", activtyId=" + execution.getCurrentActivityId()
                + ", activtyName='" + execution.getCurrentActivityName() + "'"
                + ", processInstanceId=" + execution.getProcessInstanceId()
                + ", businessKey=" + execution.getProcessBusinessKey()
                + ", executionId=" + execution.getId()
                + ", sid=" + response.getSid()
                + " \n\n");

    }
}
