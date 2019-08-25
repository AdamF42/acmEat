package it.unibo.bank;

import it.unibo.bank.generated.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import static it.unibo.utils.AcmeVariables.IS_VALID_TOKEN;
import static it.unibo.utils.AcmeVariables.USER_TOKEN;

public class VerifyBankToken implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        String token = (String) delegateExecution.getVariable(USER_TOKEN);

        Bank bankService = new BankService().getBankServicePort();
        VerifyToken verifyToken = new VerifyToken();
        verifyToken.setSid(token);
        // TODO: UT005023: Exception handling request to /acmeat-ws/confirm:
        //  javax.xml.ws.soap.SOAPFaultException: Unexpected element verifyTokenResponse found.
        //  Expected {soseng.xsd}verifyTokenResponse.
       // VerifyTokenResponse resp = bankService.verifyToken(verifyToken);

//        if(resp.isSuccess()) {
            delegateExecution.setVariable(IS_VALID_TOKEN, true);
//        }else{
//            delegateExecution.setVariable(IS_VALID_TOKEN, false);
//        }
    }
}
