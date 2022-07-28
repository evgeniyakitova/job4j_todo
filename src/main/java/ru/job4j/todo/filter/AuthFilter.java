package ru.job4j.todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        List<String> allowedPaths = List.of("/login", "/registration");
        String uri = req.getRequestURI();
        if (allowedPaths.contains(uri) || req.getSession().getAttribute("user") != null) {
            filterChain.doFilter(req, res);
            return;
        }
        res.sendRedirect(req.getContextPath() + "/login");
    }
}
