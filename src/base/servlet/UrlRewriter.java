package base.servlet;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")

public class UrlRewriter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
if(true) { chain.doFilter(request, response); return;}
        String path = req.getRequestURI();
        System.out.println(path +"  "+request.getProtocol()+" ");
        if (path.equals("/old-path")) {
            res.sendRedirect("/new-path");
        } else {
            chain.doFilter(request, response);
        }
    }
}