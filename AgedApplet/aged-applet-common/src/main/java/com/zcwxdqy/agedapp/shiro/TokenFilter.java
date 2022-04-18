package com.zcwxdqy.agedapp.shiro;

import com.zcwxdqy.agedapp.cache.Caches;
import com.zcwxdqy.agedapp.pojo.vo.result.CodeMsg;
import com.zcwxdqy.agedapp.util.JsonVos;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * 自定义 Filter
 * 作用 ：验证用户合法性、   验证用户权限
 */

public class TokenFilter extends AccessControlFilter {


    public static final String HEADER_TOKEN = "Token";


    /**
     * 【使用场景】：做初步判断
     * 【调用时期】：当请求被TokenFilter拦截时，就会调用这个方法
     * 【返回true】：允许访问。可以进入下一个链条的调用（如其他Filter、拦截器、控制器）
     * 【返回false】：不允许访问，会进入下面的 onAccessDenied 方法
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     * 【使用场景】：我们在这个方法中进行 token的校验
     * 【调用时期】：当上面的 isAccessAllowed 方法返回 false时，就会调用这个方法
     * 【返回true】：允许访问。可以进入下一个链条的调用（如其他Filter、拦截器、控制器）
     * 【返回false】：不允许访问，就不会进入下一链条的调用。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        // 取出Token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(HEADER_TOKEN);

        // 如果没有Token
        if (token == null) {
            JsonVos.raise(CodeMsg.NO_TOKEN);
        }

        // 如果Token过期了
        if (Caches.getToken(token) == null) {
            return JsonVos.raise(CodeMsg.TOKEN_EXPIRED);
        }

        // 来到这里，说明已经认证通过了，放行登录
        return true;
    }
}
