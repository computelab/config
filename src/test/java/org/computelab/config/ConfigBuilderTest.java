package org.computelab.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ConfigBuilderTest {

    @Test
    public void testDefaultName() {
        ConfigBuilder builder = new ConfigBuilder();
        builder.addJsonConfig("{}");
        Config config = builder.build();
        assertEquals(ConfigChain.class.getSimpleName(), config.name());
    }

    @Test
    public void testName() {
        ConfigBuilder builder = new ConfigBuilder();
        builder.addJsonConfig("{}");
        Config config = builder.build("config");
        assertEquals("config", config.name());
    }
}
