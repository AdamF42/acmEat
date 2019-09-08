package it.unibo.bank;

import it.unibo.bank.generated.Bank;
import it.unibo.bank.generated.BankService;
import it.unibo.bank.generated.Refound;
import it.unibo.bank.generated.RefoundResponse;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static it.unibo.utils.AcmeVariables.IS_USER_REFUNDED;
import static it.unibo.utils.AcmeVariables.USER_TOKEN;

public class AbortPayment implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String token = (String) delegateExecution.getVariable(USER_TOKEN);

        try {
            Bank bankService = new BankService().getBankServicePort();
            Refound refound = new Refound();
            refound.setSid(token);
            RefoundResponse resp = bankService.refound(refound);
            delegateExecution.setVariable(IS_USER_REFUNDED, resp.isSuccess());
        } catch (Exception e) {
            e.printStackTrace();
            delegateExecution.setVariable(IS_USER_REFUNDED, false);
        }

    }
}
