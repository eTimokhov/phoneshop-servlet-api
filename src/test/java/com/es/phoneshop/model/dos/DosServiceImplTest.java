package com.es.phoneshop.model.dos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DosServiceImplTest {

    private DosService dosService;

    @Before
    public void setup() {
        dosService = DosServiceImpl.getInstance();
    }

    @Test
    public void testIsAllowed() {
        for (int i = 0; i < 19; i++) {
            dosService.isAllowed("123.123.123.123");
        }
        assertTrue(dosService.isAllowed("123.123.123.123"));
        assertFalse(dosService.isAllowed("123.123.123.123"));
    }

}