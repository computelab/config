package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class SystemEnvConfigTest {

    @Before
    public void before() throws Exception {
        SystemEnvUtil.setEnv(getKey(), "123abc");
        SystemEnvUtil.setEnv("LIST_" + getKey(), "a,b,c");
    }

    @Test
    public void testConstructor1() {
        Config config = new SystemEnvConfig("name");
        assertEquals("name", config.name());
    }

    @Test
    public void testConstructor2() {
        Config config = new SystemEnvConfig("name", "\\s*,\\s*");
        assertEquals("name", config.name());
        assertEquals(3, config.getAsList("LIST_" + getKey()).size());
    }

    @Test
    public void testHas() {
        Config config = new SystemEnvConfig("env-config");
        assertTrue(config.has(getKey()));
        assertFalse(config.has("some.key.that.does.not.exist"));
    }

    @Test
    public void testGet() {
        Config config = new SystemEnvConfig("env-config");
        assertEquals("123abc", config.get(getKey()));
    }

    @Test(expected = ConfigEntryMissingException.class)
    public void testGetMissingKey() {
        Config config = new SystemEnvConfig("env-config");
        config.get("some.key.that.does.not.exist");
    }

    private String getKey() {
        return getClass().getSimpleName().toUpperCase() + "_KEY";
    }
}
