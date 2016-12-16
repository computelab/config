package org.computelab.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InvalidJsonExceptionTest {

    @Test
    public void testJson() {
        InvalidJsonException ex = new InvalidJsonException("json");
        assertEquals("json", ex.getJson());
    }

    @Test
    public void testJsonAndCause() {
        Throwable cause = new RuntimeException();
        InvalidJsonException ex = new InvalidJsonException("json", cause);
        assertEquals("json", ex.getJson());
        assertEquals(cause, ex.getCause());
    }
}
