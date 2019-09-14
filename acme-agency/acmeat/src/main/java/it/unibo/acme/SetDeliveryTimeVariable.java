package it.unibo.acme;

import it.unibo.models.DeliveryOrder;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.joda.time.Hours;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

import static it.unibo.utils.AcmeVariables.DELIVERY_ORDER;
import static it.unibo.utils.AcmeVariables.DELIVERY_TIME;

public class SetDeliveryTimeVariable implements JavaDelegate {

    private final Logger LOGGER = Logger.getLogger(SetDeliveryTimeVariable.class.getName());


    @Override
    public void execute(DelegateExecution delegateExecution) {

        DeliveryOrder order = (DeliveryOrder) delegateExecution.getVariable(DELIVERY_ORDER);
        LocalTime localTime = LocalTime.parse(order.delivery_time);

        Instant instant = Instant.now();
        instant = instant.atZone(ZoneOffset.UTC)
                .withHour(localTime.getHour())
                .withMinute(localTime.getMinute())
                .toInstant();
        instant.minus(1, ChronoUnit.HOURS);
        String time = instant.toString();
        LOGGER.info("Delivery Time: " + time);
        delegateExecution.setVariable(DELIVERY_TIME, time);
    }
}