package it.unibo.acme;

import it.unibo.models.DeliveryOrder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.DELIVERY_ORDER;
import static it.unibo.utils.AcmeVariables.DELIVERY_TIME;

public class SetDeliveryTimeVariable implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(SetDeliveryTimeVariable.class.getName());


    @Override
    public void execute(DelegateExecution delegateExecution) {

        DeliveryOrder order = (DeliveryOrder) delegateExecution.getVariable(DELIVERY_ORDER);
        LocalTime time = LocalTime.parse(order.delivery_time);

        Instant instant = Instant.now();
        instant = instant.atZone(ZoneOffset.UTC)
                .withHour(time.getHour())
                .withMinute(time.getMinute())
                .toInstant();

        String test = instant.toString();
        LOGGER.info("Delivery Time: " + test);
        delegateExecution.setVariable(DELIVERY_TIME, test);
    }
}