package org.simpleframework.core.aop;

import org.simpleframework.core.BeanContainer;

public class AspectWeaver {
    private BeanContainer beanContainer;

    public AspectWeaver() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doAspect() {
        // 1. 获取所有的切面类
        // 2. 将切面类按照不同的目标进行切分
        // 3. 按照不同的织入目标进行织入Aspect
    }
}
