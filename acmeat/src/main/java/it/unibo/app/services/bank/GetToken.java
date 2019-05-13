package it.unibo.app.services.bank;

import it.unibo.app.services.bank.generated.Bank;
import it.unibo.app.services.bank.generated.BankService;
import it.unibo.app.services.bank.generated.GetTokenResponse;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GetToken implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        Bank bankService = new BankService().getBankServicePort();


        it.unibo.app.services.bank.generated.GetToken payment = new it.unibo.app.services.bank.generated.GetToken();
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
