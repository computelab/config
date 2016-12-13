package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SystemPropertyConfigReaderTest {

    @Before
    public void before() {
        System.setProperty(getKey(), "123abc");
    }

    @After
    public void after() {
        System.clearProperty(getKey());
    }

    @Test
    public void test() {
        SystemPropertyConfigReader reader = new SystemPropertyConfigReader();
        assertEquals("123abc", reader.getVal(getKey()));
        assertNull(reader.getVal(getKey() + "DoesNotExist"));
    }

    private String getKey() {
        return getClass().getSimpleName();
    }
}
