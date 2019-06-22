package it.unibo.delivery;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AbortOrderDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution arg0) throws Exception {
		System.out.println("Abort Order Delegate");
	}

}
