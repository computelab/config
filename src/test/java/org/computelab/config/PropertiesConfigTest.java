package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class PropertiesConfigTest {

    private Properties properties;

    @Before
    public void before() throws IOException {
        try (InputStream inStream =
                getClass().getResourceAsStream("/test.properties")) {
            properties = new Properties();
            properties.load(inStream);
        }
    }

    @Test
    public void testConstructor1() {
        Config config = new PropertiesConfig("name", properties);
        assertEquals("name", config.name());
    }

    @Test
    public void testConstructor2() {
        Config config = new PropertiesConfig("name", properties, "\\s*,\\s*");
        assertEquals("name", config.name());
        assertEquals(2, config.getAsList("sp.provider.urls").size());
    }

    @Test(expected = NullPointerException.class)
    public void constructorPropertiesCannotBeNull1() {
        new PropertiesConfig("name", null);
    }

    @Test(expected = NullPointerException.class)
    public void constructorPropertiesCannotBeNull2() {
        new PropertiesConfig("name", null, ",");
    }

    @Test
    public void testHas() {
        Config config = new PropertiesConfig("name", properties);
        assertTrue(config.has("sp.provider.id"));
        assertFalse(config.has("sp.provider.key"));
    }

    @Test
    public void testGet() {
        Config config = new PropertiesConfig("name", properties);
        assertEquals("123", config.get("sp.provider.id"));
    }

    @Test(expected = ConfigEntryMissingException.class)
    public void testGetMissingKey() {
        Config config = new PropertiesConfig("name", properties);
        config.get("sp.provider.key");
    }
}
