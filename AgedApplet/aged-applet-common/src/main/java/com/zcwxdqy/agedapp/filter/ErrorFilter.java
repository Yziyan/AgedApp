package com.zcwxdqy.agedapp.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * 【作用】：用来处理Filter内部还未到达Controller的异常
 * 【思路】：将抛出的ServletException异常，转发到 Controller 变成自定义的异常 CommonException
 */


public class ErrorFilter implements Filter {
    public static final String ERROR_URI = "/handleError";
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        try {
            // 若都没有异常，那么就放行
            chain.doFilter(request, response);
        } catch (Exception e) {
            // 出现异常将其转发到Controller，变成自定义的CommonException
            request.setAttribute(ERROR_URI, e);
            request.getRequestDispatcher(ERROR_URI).forward(request, response);

        }



    }
}
