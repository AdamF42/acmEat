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
        LocalTime localTime = LocalTime.parse(order.delivery_time);

        Instant orderCancellationTime = Instant.now()
                .atZone(ZoneOffset.UTC)
                .withHour(localTime.getHour() - 1)
                .withMinute(localTime.getMinute())
                .toInstant();
        LOGGER.info("Delivery Time: " + orderCancellationTime.toString());

        Instant currentTime = Instant.now()
                .atZone(ZoneOffset.UTC)
                .toInstant();

        int hour = orderCancellationTime.atZone(ZoneOffset.UTC).getHour();
        int minutes = orderCancellationTime.atZone(ZoneOffset.UTC).getMinute();
        int currentHour = currentTime.atZone(ZoneOffset.UTC).getHour();
        int currentMinute = orderCancellationTime.atZone(ZoneOffset.UTC).getMinute();

        if (!(currentHour > hour || (currentHour == hour && currentMinute > minutes))) {
            delegateExecution.setVariable(DELIVERY_TIME, orderCancellationTime);
        }
    }
}