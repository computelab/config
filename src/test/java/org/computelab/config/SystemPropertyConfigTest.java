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
    }

    @After
    public void after() {
        System.clearProperty(getKey());
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
