package com.es.phoneshop.web.filters;

import com.es.phoneshop.model.dos.DosService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DosFilterTest {

    private DosFilter dosFilter = new DosFilter();

    @Mock
    private DosService dosService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;

    @Before
    public void setup() {
        dosFilter.setDosService(dosService);
        when(request.getRemoteAddr()).thenReturn("111.111.111.111");
    }

    @Test
    public void testDoFilterIsAllowed() throws IOException, ServletException {
        when(dosService.isAllowed(anyString())).thenReturn(true);
        dosFilter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    public void testDoFilterIsNotAllowed() throws IOException, ServletException {
        when(dosService.isAllowed(anyString())).thenReturn(false);
        dosFilter.doFilter(request, response, chain);
        verify(response).sendError(eq(429), anyString());
    }

}