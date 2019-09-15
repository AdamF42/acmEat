package it.unibo.utils;

import it.unibo.factory.ResponseFactory;
import org.camunda.bpm.engine.ProcessEngine;

import javax.inject.Inject;

public class AcmeatWsHttpServlet extends ApiHttpServlet{

    @Inject
    public ProcessEngine processEngine;

    @Inject
    public ResponseFactory responseFactory;

}
