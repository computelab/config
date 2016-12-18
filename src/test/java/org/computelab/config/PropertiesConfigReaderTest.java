package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class PropertiesConfigReaderTest {

    private Properties properties;

    @Before
    public void before() throws IOException {
        try (InputStream inStream =
                getClass().getResourceAsStream("/test.properties")) {
            properties = new Properties();
            properties.load(inStream);
        }
    }

    @Test(expected=NullPointerException.class)
    public void constructorPropertiesCannotBeNull() {
        new PropertiesConfigReader(null);
    }

    @Test
    public void test() {
        PropertiesConfigReader reader = new PropertiesConfigReader(properties);
        assertEquals("567", reader.getVal("sp.researcher.id"));
        assertEquals("abc", reader.getVal("sp.provider.secret"));
        assertEquals("http://provider.test.com/, http://provider.test2.com/",
                reader.getVal("sp.provider.urls"));
    }

    @Test
    public void testMissingKey() {
        PropertiesConfigReader reader = new PropertiesConfigReader(properties);
        assertNull(reader.getVal("sp.researcher.key"));
    }

    @Test
    public void emptyPropertiesAreAllowed() {
        PropertiesConfigReader reader = new PropertiesConfigReader(
                new Properties());
        assertNull(reader.getVal("sp.researcher.id"));
    }
}
