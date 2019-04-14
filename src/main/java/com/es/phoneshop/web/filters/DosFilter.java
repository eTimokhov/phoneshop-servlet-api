package com.es.phoneshop.web.filters;


import com.es.phoneshop.model.dos.DosService;
import com.es.phoneshop.model.dos.DosServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DosFilter implements Filter {

    private DosService dosService;

    @Override
    public void init(FilterConfig filterConfig) {
        dosService = DosServiceImpl.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean isAllowed = dosService.isAllowed(request.getRemoteAddr());
        if (isAllowed) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse)response).sendError(429, "Too many requests");
        }
    }

    @Override
    public void destroy() {
    }

    public void setDosService(DosService dosService){
        this.dosService = dosService;
    }
}
