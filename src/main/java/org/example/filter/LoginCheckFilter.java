package org.example.filter;

/**
 * @author Christy Guo
 * @create 2023-03-28 12:20 AM
 */

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseContext;
import org.example.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter{
    //ath matcher, wildcard support support
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

         String requestURI = request.getRequestURI();// /backend/index.html

         log.info("Intercepted request: {}", requestURI);



        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
//
//         //2. Determine whether this request needs to be processed
        boolean check = check(urls, requestURI);
//
//         //3. If no processing is required, release it directly
        if(check){
            log.info("This request {} does not need to be processed", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

//         //4-1. Determine the login status, if it is already logged in, it will be released directly
        if(request.getSession().getAttribute("employee") != null){
            log.info("The user is logged in, the user id is: {}", request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

//
//         //4-2. Determine the login status, if it is already logged in, it will be released directly
        if(request.getSession().getAttribute("user") != null){
            log.info("The user is logged in, the user id is: {}", request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request, response);
            return;
        }
//
//         //5. If not logged in, return the result of not logged in, and respond data to the client page through the output stream
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * Path matching, check whether this request needs to be released
     * @param urls
     * @param requestURI
     * @return
     */

    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}