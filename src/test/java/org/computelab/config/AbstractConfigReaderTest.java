package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AbstractConfigReaderTest {

    @Test
    public void testGet() {
        ConfigReader<String, String> reader = mockConfigReader("val");
        ConfigEntry<String, String> entry = reader.get("key");
        assertEquals("key", entry.key());
        assertEquals("val", entry.value());
    }

    @Test
    public void testHas() {
        ConfigReader<String, String> reader = mockConfigReader("val");
        assertTrue(reader.has("key"));
    }

    @Test(expected=ConfigEntryMissingException.class)
    public void getThrowsExceptionOnNull() {
        ConfigReader<String, String> reader = mockConfigReader(null);
        reader.get("key");
    }

    @Test
    public void hasReturnsFalseOnNull() {
        ConfigReader<String, String> reader = mockConfigReader(null);
        assertFalse(reader.has("key"));
    }

    @Test(expected=NullPointerException.class)
    public void getKeyCannotBeNull() {
        ConfigReader<String, String> reader = mockConfigReader("val");
        reader.get(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getKeyCannotBeEmpty() {
        ConfigReader<String, String> reader = mockConfigReader("val");
        reader.get("");
    }

    @Test(expected=NullPointerException.class)
    public void hasKeyCannotBeNull() {
        ConfigReader<String, String> reader = mockConfigReader("val");
        reader.has(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void hasKeyCannotBeEmpty() {
        ConfigReader<String, String> reader = mockConfigReader("val");
        reader.has("");
    }

    private AbstractConfigReader<String> mockConfigReader(String val) {
        return new AbstractConfigReader<String>() {
            @Override
            String getVal(String key) {
                return val;
            }
        };
    }
}
