package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class ConfigBuilderTest {

    @Test
    public void testDefaultName() {
        ConfigBuilder builder = new ConfigBuilder();
        builder.addSystemEnvConfig();
        Config config = builder.build();
        assertEquals(ConfigChain.class.getSimpleName(), config.name());
    }

    @Test
    public void testName() {
        ConfigBuilder builder = new ConfigBuilder();
        builder.addSystemEnvConfig();
        Config config = builder.build("config");
        assertEquals("config", config.name());
    }

    @Test
    public void testAdd() throws IllegalAccessException {
        ConfigBuilder builder = new ConfigBuilder();
        builder.addSystemPropertyConfig();
        builder.addSystemEnvConfig();
        builder.addJsonConfig("{}");
        builder.addPropertiesConfig(new Properties());
        Config config = builder.build();
        assertTrue(config instanceof ConfigChain);
        List<Config> configs = (List<Config>)FieldUtils.readField(config, "configs", true);
        assertNotNull(configs);
        assertEquals(4, configs.size());
        assertTrue(configs.get(0) instanceof SystemPropertyConfig);
        assertEquals(SystemPropertyConfig.class.getSimpleName(), configs.get(0).name());
        assertTrue(configs.get(1) instanceof SystemEnvConfig);
        assertEquals(SystemEnvConfig.class.getSimpleName(), configs.get(1).name());
        assertTrue(configs.get(2) instanceof JsonConfig);
        assertEquals(JsonConfig.class.getSimpleName(), configs.get(2).name());
        assertTrue(configs.get(3) instanceof PropertiesConfig);
        assertEquals(PropertiesConfig.class.getSimpleName(), configs.get(3).name());
    }

    @Test
    public void testAddWithSplitRegex() throws IllegalAccessException {
        ConfigBuilder builder = new ConfigBuilder();
        builder.addSystemPropertyConfig("A", "regexA");
        builder.addSystemEnvConfig("B", "regexB");
        builder.addPropertiesConfig("C", new Properties(), "regexC");
        Config config = builder.build();
        assertTrue(config instanceof ConfigChain);
        List<Config> configs = (List<Config>)FieldUtils.readField(config, "configs", true);
        assertNotNull(configs);
        assertEquals(3, configs.size());
        assertTrue(configs.get(0) instanceof SystemPropertyConfig);
        assertEquals("A", configs.get(0).name());
        assertEquals("regexA", FieldUtils.readField(configs.get(0), "splitRegex", true).toString());
        assertTrue(configs.get(1) instanceof SystemEnvConfig);
        assertEquals("B", configs.get(1).name());
        assertEquals("regexB", FieldUtils.readField(configs.get(1), "splitRegex", true).toString());
        assertTrue(configs.get(2) instanceof PropertiesConfig);
        assertEquals("C", configs.get(2).name());
        assertEquals("regexC", FieldUtils.readField(configs.get(2), "splitRegex", true).toString());
    }
}
