package it.unibo.soseng.delivery;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GetOrderDelegate implements JavaDelegate{
	
	@Override
    public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Get Order Delegate");

        /*int userInput = 2;
        TwiceService twiceService = new TwiceServiceService().getTwiceServiceServicePort();
        Twice twice = new Twice();
        twice.setA(userInput);
        twice.setB(3);

        TwiceResponse response = twiceService.twice(twice);
        if (response.getC() == userInput*2){
            execution.setVariable("weatherOk", true);
        }
        Random rando = new Random();

        execution.setVariable("name", "Adam");
        execution.setVariable("weatherOk", rando.nextBoolean());

        execution.setVariable("d", response.getD());
        execution.setVariable("weatherOk", response.getD());
*/
    }

}
