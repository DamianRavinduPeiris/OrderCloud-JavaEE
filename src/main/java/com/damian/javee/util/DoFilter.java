package com.damian.javee.util;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DoFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getHeader("Origin").contains("http://http://localhost:63342/OrderCloud-JaveEE-master/src/main/java/com/damian/javee/frontend/")) {
            res.setHeader("Access-Control-Allow-Origin", "http://localhost:63342/OrderCloud-JaveEE-master/src/main/java/com/damian/javee/frontend/");
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE,OPTIONS, HEAD, PATCH");
            res.setHeader("Access-Control-Allow-Headers", "Content-Type");
            res.setHeader("Access-Control-Expose-Headers", "Content-Type");
            res.setHeader("Content-Type", "application/json");


        }
        chain.doFilter(req, res);
    }
}
