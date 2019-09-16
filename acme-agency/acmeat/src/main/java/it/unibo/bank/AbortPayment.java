package it.unibo.bank;

import it.unibo.bank.generated.Bank;
import it.unibo.bank.generated.BankService;
import it.unibo.bank.generated.Refound;
import it.unibo.bank.generated.RefoundResponse;
import it.unibo.models.DeliveryOrder;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

import static it.unibo.utils.AcmeErrorMessages.UNAVAILABLE_BANK;
import static it.unibo.utils.AcmeVariables.USER_TOKEN;

public class AbortPayment implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(JavaDelegate.class.getName());

    @Override
    public void execute(DelegateExecution delegateExecution) {

        String token = (String) delegateExecution.getVariable(USER_TOKEN);

        try {
            Bank bankService = new BankService().getBankServicePort();
            Refound refound = new Refound();
            refound.setSid(token);
            RefoundResponse resp = bankService.refound(refound);
            LOGGER.info("AbortPayment returned: " + resp.isSuccess());
            if (!resp.isSuccess()){
                throw new BpmnError(UNAVAILABLE_BANK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BpmnError(UNAVAILABLE_BANK);
        }
    }
}
