package org.simpleframework.mvc;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.mvc.processor.RequestProcessor;
import org.simpleframework.mvc.render.DefaultResultRender;
import org.simpleframework.mvc.render.InternalErrorResultRender;
import org.simpleframework.mvc.render.ResultRender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;

@Data
@Slf4j
public class RequestProcessorChain {
    private Iterator<RequestProcessor> requestProcessorIterator;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String requestMethod;
    private String requestPath;
    private int responseCode;
    private ResultRender resultRender;

    public RequestProcessorChain(Iterator<RequestProcessor> iterator, HttpServletRequest request, HttpServletResponse response) {
        this.requestProcessorIterator = iterator;
        this.request = request;
        this.response = response;
        this.requestMethod = request.getMethod();
        this.requestPath = request.getPathInfo();
        this.responseCode = HttpServletResponse.SC_OK;
    }

    /**
     * 以责任链的模式执行请求链
     */
    public void doRequestProcessorChain() {
        //1。通过迭代器遍历注册的请求处理器实现类列表
        try {
            //2。知道某个请求返回false为止
            while (requestProcessorIterator.hasNext()) {
                if (!requestProcessorIterator.next().process(this)) {
                    break;
                }
            }
        } catch (Exception e) {
            //3。期间如果出现异常，则交由内部异常渲染器处理
            this.resultRender = new InternalErrorResultRender();
            log.error("Exception in RequestProcessorChain when doRequestProcessorChain");
        }
    }

    public void doRender() {
        //1. 如果请求处理器都没有选择合适的渲染器，则使用默认的
        if (this.resultRender == null) {
            this.resultRender = new DefaultResultRender();
        }
        //2. 调用渲染器的render方法对结果进行渲染
        try {
            this.resultRender.render();
        } catch (Exception e) {
            log.error("Exception in RequestProcessorChain when doRender");
            throw new RuntimeException();
        }
    }
}
