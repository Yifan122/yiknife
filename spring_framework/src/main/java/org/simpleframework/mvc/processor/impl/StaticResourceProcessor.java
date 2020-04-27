package org.simpleframework.mvc.processor.impl;

import org.simpleframework.mvc.RequestProcessorChain;
import org.simpleframework.mvc.processor.RequestProcessor;

public class StaticResourceProcessor implements RequestProcessor {
    @Override
    public boolean process(RequestProcessorChain reqestProcessorChain) throws Exception {
        return false;
    }
}
