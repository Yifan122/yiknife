package org.simpleframework.mvc.processor;

import org.simpleframework.mvc.RequestProcessorChain;

public interface RequestProcessor {
    boolean process(RequestProcessorChain reqestProcessorChain) throws Exception;
}
