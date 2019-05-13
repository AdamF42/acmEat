package it.unibo.app.services.bank;

import it.unibo.app.services.bank.generated.Bank;
import it.unibo.app.services.bank.generated.BankService;
import it.unibo.app.services.bank.generated.RefoundResponse;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class VerifyToken implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        it.unibo.app.services.bank.generated.VerifyToken token = (it.unibo.app.services.bank.generated.VerifyToken) execution.getVariable("bankToken");

        Bank bankService = new BankService().getBankServicePort();

        RefoundResponse response = bankService.verifyToken(token);

        System.out.println("\n\n  ... VerifyToken invoked by "
                + "processDefinitionId=" + execution.getProcessDefinitionId()
                + ", activtyId=" + execution.getCurrentActivityId()
                + ", activtyName='" + execution.getCurrentActivityName() + "'"
                + ", processInstanceId=" + execution.getProcessInstanceId()
                + ", businessKey=" + execution.getProcessBusinessKey()
                + ", executionId=" + execution.getId()
                + ", sid=" + response.isSuccess()
                + " \n\n");

    }
}
