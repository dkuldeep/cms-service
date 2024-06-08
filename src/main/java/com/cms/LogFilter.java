package com.cms;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LogFilter implements Filter {
    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info(Stream.of(
                servletRequest.getRemoteAddr(),
                servletRequest.getRemoteHost(),
                String.valueOf(servletRequest.getRemotePort()))
                .collect(Collectors.joining(",", "[", "]")));
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
