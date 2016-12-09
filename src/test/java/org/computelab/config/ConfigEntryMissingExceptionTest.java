package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConfigEntryMissingExceptionTest {

    @Test
    public void testGetMessage() {
        Exception e = new ConfigEntryMissingException("somekey");
        assertTrue(e.getMessage().contains("somekey"));
    }

    @Test
    public void testKey() {
        ConfigEntryMissingException e = new ConfigEntryMissingException("key");
        assertEquals("key", e.key());
    }
}
