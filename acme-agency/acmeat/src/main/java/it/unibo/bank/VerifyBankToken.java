package it.unibo.bank;

import it.unibo.bank.generated.Bank;
import it.unibo.bank.generated.BankService;
import it.unibo.bank.generated.VerifyToken;
import it.unibo.bank.generated.VerifyTokenResponse;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.*;

public class VerifyBankToken implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(JavaDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String token = (String) delegateExecution.getVariable(USER_TOKEN);

        try {
            Bank bankService = new BankService().getBankServicePort();
            VerifyToken verifyToken = new VerifyToken();
            verifyToken.setSid(token);
            VerifyTokenResponse resp = bankService.verifyToken(verifyToken);
            delegateExecution.setVariable(IS_VALID_TOKEN, resp.isSuccess());
            LOGGER.info("IsValidToken: " + resp.isSuccess());
            delegateExecution.setVariable(IS_UNREACHABLE_BANK_SERVICE, false);
        } catch (Exception e) {
            e.printStackTrace();
            delegateExecution.setVariable(IS_VALID_TOKEN, false);
            delegateExecution.setVariable(IS_UNREACHABLE_BANK_SERVICE, true);
            LOGGER.info("IsValidToken: false");
        }
    }
}