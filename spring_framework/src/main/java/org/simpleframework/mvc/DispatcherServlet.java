package org.simpleframework.mvc;

import org.simpleframework.core.BeanContainer;
import org.simpleframework.core.aop.AspectWeaver;
import org.simpleframework.core.inject.DependencyInjector;
import org.simpleframework.mvc.processor.RequestProcessor;
import org.simpleframework.mvc.processor.impl.ControllerRequestProcessor;
import org.simpleframework.mvc.processor.impl.JspRequestProcessor;
import org.simpleframework.mvc.processor.impl.PreRequestProcessor;
import org.simpleframework.mvc.processor.impl.StaticResourceProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {
    List<RequestProcessor> REQUESTPROCESSOR = new ArrayList<>();
    BeanContainer beanContainer;

    @Override
    public void init() throws ServletException {
        // Initialize Bean Container
        beanContainer = BeanContainer.getInstance();
        beanContainer.loadContainer("com.imooc");
        new AspectWeaver().doAop();
        new DependencyInjector().doIoc();
        // Initialize processor chain
        REQUESTPROCESSOR.add(new PreRequestProcessor());
        REQUESTPROCESSOR.add(new StaticResourceProcessor());
        REQUESTPROCESSOR.add(new JspRequestProcessor());
        REQUESTPROCESSOR.add(new ControllerRequestProcessor());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. Create RequestProcessorChain instance
        RequestProcessorChain requestProcessorChain = new RequestProcessorChain(REQUESTPROCESSOR.iterator(), req, resp);
        //2. Handle the Request using RequestProcessorChain
        requestProcessorChain.doRequestProcessorChain();
        //3. Render the result
        requestProcessorChain.doRender();

    }
}
