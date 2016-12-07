package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class AbstractConfigTest {

    @Test
    public void name() {
        Config config = mockConstructor("name");
        assertEquals("name", config.name());
    }

    @Test(expected=NullPointerException.class)
    public void nameCannotBeNull() {
        mockConstructor(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void nameCannotBeEmpty() {
        mockConstructor("");
    }

    @Test
    public void getAsBoolean() {
        String key = "anyKey";
        // True scenarios
        Config config = mockGet("True");
        assertTrue(config.getAsBoolean(key));
        config = mockGet("true");
        assertTrue(config.getAsBoolean(key));
        config = mockGet("TRUE");
        assertTrue(config.getAsBoolean(key));
        // False scenarios
        config = mockGet("False");
        assertFalse(config.getAsBoolean(key));
        config = mockGet(null);
        assertFalse(config.getAsBoolean(key));
        config = mockGet("NotBoolean");
        assertFalse(config.getAsBoolean(key));
        config = mockGet("yes");
        assertFalse(config.getAsBoolean(key));
        config = mockGet("1");
        assertFalse(config.getAsBoolean(key));
    }

    @Test
    public void getAsInteger() {
        Config config = mockGet("5");
        assertEquals(5, config.getAsInt("anyKey"));
    }

    @Test(expected=NumberFormatException.class)
    public void getAsIntegerNumberFormatException() {
        Config config = mockGet("NotAnInteger");
        config.getAsInt("anyKey");
    }

    @Test
    public void getAsLong() {
        Config config = mockGet("5");
        assertEquals(5L, config.getAsLong("anyKey"));
    }

    @Test(expected=NumberFormatException.class)
    public void getAsLongNumberFormatException() {
        Config config = mockGet("NotAnInteger");
        config.getAsLong("anyKey");
    }

    @Test(expected=NullPointerException.class)
    public void getKeyCannotBeNull() {
        Config config = mockGet("9");
        config.getAsLong(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void getKeyCannotBeEmpty() {
        Config config = mockGet("9");
        config.getAsLong("");
    }

    private Config mockConstructor(String name) {
        return mockConfig(name, null, null);
    }

    private Config mockGet(String get) {
        return mockConfig("name", get, null);
    }

    private Config mockConfig(String name, String get, List<String> getAsList) {
        return new AbstractConfig(name) {
            @Override
            public boolean has(String key) {
                return get != null;
            }
            @Override
            public String get(String key) {
                return get;
            }
            @Override
            public List<String> getAsList(String key) {
                return getAsList;
            }
        };
    }
}
