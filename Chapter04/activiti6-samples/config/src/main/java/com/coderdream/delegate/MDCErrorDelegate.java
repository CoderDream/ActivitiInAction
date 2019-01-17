package com.coderdream.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MDCErrorDelegate implements JavaDelegate {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(MDCErrorDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        LOGGER.info("run MDCErrorDelegate");
        throw new RuntimeException("only test");
    }
}
