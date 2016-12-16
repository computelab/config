package org.computelab.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UnexpectedJsonTypeExceptionTest {

    @Test
    public void testJson() {
        UnexpectedJsonTypeException ex = new UnexpectedJsonTypeException("json");
        assertEquals("json", ex.getJson());
    }

    @Test
    public void testJsonAndCause() {
        Throwable cause = new RuntimeException();
        UnexpectedJsonTypeException ex = new UnexpectedJsonTypeException("json", cause);
        assertEquals("json", ex.getJson());
        assertEquals(cause, ex.getCause());
    }
}
