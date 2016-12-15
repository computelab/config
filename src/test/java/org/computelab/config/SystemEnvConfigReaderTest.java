package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SystemEnvConfigReaderTest {

    @Before
    public void before() throws Exception {
        setEnv(getKey(), "123abc");
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

    // http://stackoverflow.com/questions/318239
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void setEnv(String key, String val) throws Exception {
        Class[] classes = Collections.class.getDeclaredClasses();
        Map<String, String> env = System.getenv();
        for (Class cl : classes) {
            if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                Field field = cl.getDeclaredField("m");
                field.setAccessible(true);
                Object obj = field.get(env);
                Map<String, String> map = (Map<String, String>) obj;
                map.put(key, val);
            }
        }
    }

    private String getKey() {
        return getClass().getSimpleName().toUpperCase() + "_KEY";
    }
}
