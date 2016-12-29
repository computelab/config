package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class StackedConfigTest {

    private StackedConfig config;

    @Before
    public void before() {
        final Properties topProps = new Properties();
        topProps.put("key1", "top1");
        topProps.put("key2", "top2");
        final Config topConfig = new PropertiesConfig("top", topProps);
        final Properties midProps = new Properties();
        midProps.put("key1", "mid1");
        midProps.put("key3", "mid3");
        midProps.put("list", "a,b,c");
        final Config midConfig = new PropertiesConfig("mid", midProps);
        config = new StackedConfig(getClass().getSimpleName(),
                Arrays.asList(topConfig, midConfig));
    }

    @Test
    public void shouldNotFallbackWhenTopExists() {
        assertEquals("top1", config.get("key1"));
        assertEquals("top2", config.get("key2"));
    }

    @Test
    public void shouldFallbackWhenTopMissing() {
        assertEquals("mid3", config.get("key3"));
    }

    @Test
    public void testHas() {
        assertTrue(config.has("key1"));
        assertTrue(config.has("key2"));
        assertTrue(config.has("key3"));
        assertFalse(config.has("key4"));
    }

    @Test
    public void testGetList() {
        List<String> list = config.getAsList("list");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));
    }

    @Test(expected=ConfigEntryMissingException.class)
    public void getThrowsExceptionWhenKeyNotExist() {
        config.get("key4");
    }

    @Test(expected=ConfigEntryMissingException.class)
    public void getAsListThrowsExceptionWhenKeyNotExist() {
        config.getAsList("key4");
    }

    @Test(expected=NullPointerException.class)
    public void constructorConfigsCannotBeNull() {
        new StackedConfig(getClass().getSimpleName(), null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorConfigsCannotBeEmpty() {
        new StackedConfig(getClass().getSimpleName(),
                Arrays.asList());
    }
}
