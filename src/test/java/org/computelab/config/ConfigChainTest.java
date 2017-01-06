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

public class ConfigChainTest {

    private ConfigChain configChain;

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
        configChain = new ConfigChain(getClass().getSimpleName(),
                Arrays.asList(topConfig, midConfig));
    }

    @Test
    public void shouldNotFallbackWhenTopExists() {
        assertEquals("top1", configChain.get("key1"));
        assertEquals("top2", configChain.get("key2"));
    }

    @Test
    public void shouldFallbackWhenTopMissing() {
        assertEquals("mid3", configChain.get("key3"));
    }

    @Test
    public void testHas() {
        assertTrue(configChain.has("key1"));
        assertTrue(configChain.has("key2"));
        assertTrue(configChain.has("key3"));
        assertFalse(configChain.has("key4"));
    }

    @Test
    public void testGetList() {
        List<String> list = configChain.getAsList("list");
        assertNotNull(list);
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));
    }

    @Test(expected=ConfigEntryMissingException.class)
    public void getThrowsExceptionWhenKeyNotExist() {
        configChain.get("key4");
    }

    @Test(expected=ConfigEntryMissingException.class)
    public void getAsListThrowsExceptionWhenKeyNotExist() {
        configChain.getAsList("key4");
    }

    @Test(expected=NullPointerException.class)
    public void constructorConfigsCannotBeNull() {
        new ConfigChain(getClass().getSimpleName(), null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorConfigsCannotBeEmpty() {
        new ConfigChain(getClass().getSimpleName(),
                Arrays.asList());
    }
}
