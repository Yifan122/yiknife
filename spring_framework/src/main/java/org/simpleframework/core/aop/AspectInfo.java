package org.simpleframework.core.aop;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AspectInfo {
    private int index;
    private DefaultAspect defaultAspect;
}
