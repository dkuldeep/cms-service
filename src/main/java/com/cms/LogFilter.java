package com.cms;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.StringJoiner;
import java.util.logging.Logger;

@Component
public class LogFilter implements Filter {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        joiner.add(servletRequest.getRemoteAddr());
        joiner.add(servletRequest.getRemoteHost());
        joiner.add(String.valueOf(servletRequest.getRemotePort()));
        logger.info(joiner.toString());
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
