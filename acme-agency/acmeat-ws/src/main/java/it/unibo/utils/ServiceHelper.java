package it.unibo.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.camunda.bpm.engine.RuntimeService;
import java.util.Map;

public class ServiceHelper {

    private static final Logger LOGGER = LogManager.getLogger("SeriviceHelper");

    public static String verifySession(String sessionId, RuntimeService service) {
        if(sessionId==null) {
            LOGGER.warn("sessionId is null");
            return "";
        }

        Map<String, Object> processVariables = service.getVariables(sessionId);
        if (processVariables == null || processVariables.size() == 0 ){
            return "";
        }
        return sessionId;
    }
}
