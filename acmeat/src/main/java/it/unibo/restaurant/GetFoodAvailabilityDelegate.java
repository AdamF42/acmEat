package it.unibo.restaurant;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GetFoodAvailabilityDelegate implements JavaDelegate {

	    @Override
	    public void execute(DelegateExecution execution) throws Exception {

	        System.out.println("Get food availability delegate");


	    }
}