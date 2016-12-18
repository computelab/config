package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertyConfigTest {

    @Before
    public void before() {
        System.setProperty(getKey(), "123abc");
        System.setProperty(getKey() + "List", "1, 2, 3");
    }

    @After
    public void after() {
        System.clearProperty(getKey());
        System.clearProperty(getKey() + "List");
    }

    @Test
    public void testConstructor1() {
        Config config = new SystemPropertyConfig("name");
        assertEquals("name", config.name());
    }

    @Test
    public void testConstructor2() {
        Config config = new SystemPropertyConfig("name", "\\s*,\\s*");
        assertEquals("name", config.name());
        assertEquals(3, config.getAsList(getKey() + "List").size());
    }

    @Test
    public void testHas() {
        Config config = new SystemPropertyConfig("system-property-config");
        assertTrue(config.has(getKey()));
        assertFalse(config.has(getKey() + "DoesNotExist"));
    }

    @Test
    public void testGet() {
        Config config = new SystemPropertyConfig("system-property-config");
        assertEquals("123abc", config.get(getKey()));
    }

    @Test(expected = ConfigEntryMissingException.class)
    public void testGetMissingKey() {
        Config config = new SystemPropertyConfig("system-property-config");
        config.get(getKey() + "DoesNotExist");
    }

    private String getKey() {
        return getClass().getSimpleName();
    }
}
