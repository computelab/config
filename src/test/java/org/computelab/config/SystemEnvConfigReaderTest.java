package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class SystemEnvConfigReaderTest {

    @Before
    public void before() throws Exception {
        SystemEnvUtil.setEnv(getKey(), "123abc");
    }

    @Test
    public void test() {
        SystemEnvConfigReader reader = new SystemEnvConfigReader();
        assertEquals("123abc", reader.getVal(getKey()));
    }

    @Test
    public void testKeyIsProperlyConverted() {
        SystemEnvConfigReader reader = new SystemEnvConfigReader();
        String val = reader.getVal(getClass().getSimpleName() + ".key");
        assertEquals("123abc", val);
    }

    @Test
    public void testMissingKey() {
        SystemEnvConfigReader reader = new SystemEnvConfigReader();
        assertNull(reader.getVal("some.random.key.not.exist"));
    }

    private String getKey() {
        return getClass().getSimpleName().toUpperCase() + "_KEY";
    }
}
