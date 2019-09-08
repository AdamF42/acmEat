package it.unibo.bank;

import it.unibo.bank.generated.Bank;
import it.unibo.bank.generated.BankService;
import it.unibo.bank.generated.VerifyToken;
import it.unibo.bank.generated.VerifyTokenResponse;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static it.unibo.utils.AcmeVariables.IS_VALID_TOKEN;
import static it.unibo.utils.AcmeVariables.USER_TOKEN;

public class VerifyBankToken implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String token = (String) delegateExecution.getVariable(USER_TOKEN);

        try {
            Bank bankService = new BankService().getBankServicePort();
            VerifyToken verifyToken = new VerifyToken();
            verifyToken.setSid(token);
            VerifyTokenResponse resp = bankService.verifyToken(verifyToken);
            delegateExecution.setVariable(IS_VALID_TOKEN, resp.isSuccess());
        } catch (Exception e) {
            e.printStackTrace();
            delegateExecution.setVariable(IS_VALID_TOKEN, false);
        }

    }
}
