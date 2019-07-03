package com.bs.library.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Slf4j
@Component
public class LoggingFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        if (servletRequest instanceof HttpServletRequest
                && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            HttpServletRequest requestToCache = new ContentCachingRequestWrapper(request);
            HttpServletResponse responseToCache = new ContentCachingResponseWrapper(response);
            chain.doFilter(requestToCache, responseToCache);

            String requestLog = String.format("REQUEST: | method: %s | path: %s | headers: %s | body: %s",
                    requestToCache.getMethod(), request.getRequestURI(), headersMapRequest(request), getRequestData(requestToCache));
            log.info(requestLog);
            String responseLog = String.format("RESPONSE: | method: %s | path: %s | headers: %s | body: %s",
                    requestToCache.getMethod(), request.getRequestURI(), headersMapResponse(response).toString(), getResponseData(responseToCache));
            log.info(responseLog);

        } else {
            chain.doFilter(servletRequest, servletResponse);
        }

    }

    private static Optional getRequestData(final HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        return Optional.ofNullable(wrapper)
                .map(ContentCachingRequestWrapper::getContentAsByteArray)
                .map(buf -> {
                    try {
                        return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return Optional.empty();
                    }
                });

    }

    private static Optional getResponseData(final HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        return Optional.ofNullable(wrapper)
                .map(ContentCachingResponseWrapper::getContentAsByteArray)
                .map(buf -> {
                    try {
                        wrapper.copyBodyToResponse();
                        return new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return Optional.empty();
                    }
                });
    }

    private Map<String, String> headersMapRequest(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> headersMapResponse(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }

    @Override
    public void destroy() {
    }
}