package com.amalitech.securesail.amalisecuresail.global.context_holders;

import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestContextHolder {
    /*
    * This class is used to store the http servlet request in thread local
    * This is so I can access the http servlet request anywhere necessary without passing it through context
     */

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<>();

    public static void setRequest(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static HttpServletRequest getRequest() {
        return requestHolder.get();
    }

    public static void clear() {
        requestHolder.remove();
    }

    private HttpRequestContextHolder() {
    }
}
