package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class DefaultConfigBuilderTest {

    @Test
    public void testConstructor() throws IllegalAccessException {
        final String appName = getClass().getSimpleName();
        final DefaultConfigBuilder builder = new DefaultConfigBuilder(appName);
        assertEquals(appName, FieldUtils.readField(
                builder, "appName", true).toString());
        assertEquals("app.properties", FieldUtils.readField(
                builder, "configFile", true).toString());
    }

    @Test(expected=NullPointerException.class)
    public void constructorAppNameCannotBeNull() {
        new DefaultConfigBuilder(null, "configFile");
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorAppNameCannotBeEmpty() {
        new DefaultConfigBuilder("", "configFile");
    }

    @Test(expected=NullPointerException.class)
    public void constructorConfigFileCannotBeNull() {
        new DefaultConfigBuilder("app", null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void constructorConfigFileCannotBeEmpty() {
        new DefaultConfigBuilder("app", "");
    }

    @Test
    public void testBuild() throws IllegalAccessException {
        final String appName = getClass().getSimpleName();
        final DefaultConfigBuilder builder = new DefaultConfigBuilder(appName);
        final Config config = builder.build();
        assertTrue(config instanceof ConfigChain);
        final List<Config> configs = (List<Config>)FieldUtils.readField(config, "configs", true);
        assertNotNull(configs);
        assertEquals(2, configs.size());
        assertTrue(configs.get(0) instanceof SystemPropertyConfig);
        assertTrue(configs.get(1) instanceof SystemEnvConfig);
    }

    @Test
    public void testBuildWithResource() throws IllegalAccessException {
        final String appName = getClass().getSimpleName();
        final DefaultConfigBuilder builder = new DefaultConfigBuilder(appName, "test.properties");
        assertEquals("test.properties", FieldUtils.readField(
                builder, "configFile", true).toString());
        final Config config = builder.build();
        assertTrue(config instanceof ConfigChain);
        final List<Config> configs = (List<Config>)FieldUtils.readField(config, "configs", true);
        assertNotNull(configs);
        assertEquals(3, configs.size());
        assertTrue(configs.get(0) instanceof SystemPropertyConfig);
        assertTrue(configs.get(1) instanceof SystemEnvConfig);
        assertTrue(configs.get(2) instanceof PropertiesConfig);
        assertEquals(123, config.getAsInt("sp.provider.id"));
    }
}
