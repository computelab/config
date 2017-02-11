package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class DefaultConfigTest {

    @Test
    public void testCreate() {
        final String appName = getClass().getSimpleName();
        final Config config = DefaultConfig.create(appName);
        assertNotNull(config);
    }

    @Test(expected=NullPointerException.class)
    public void createAppNameCannotBeNull() {
        DefaultConfig.create(null, "configFile");
    }

    @Test(expected=IllegalArgumentException.class)
    public void createAppNameCannotBeEmpty() {
        DefaultConfig.create("", "configFile");
    }

    @Test(expected=NullPointerException.class)
    public void createConfigFileCannotBeNull() {
        DefaultConfig.create("app", null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void createConfigFileCannotBeEmpty() {
        DefaultConfig.create("app", "");
    }

    @Test
    public void testBuilderConstructor() throws IllegalAccessException {
        final String appName = getClass().getSimpleName();
        final DefaultConfig.Builder builder = new DefaultConfig.Builder(appName, "app.properties");
        assertEquals(appName, FieldUtils.readField(
                builder, "appName", true).toString());
        assertEquals("app.properties", FieldUtils.readField(
                builder, "configFile", true).toString());
    }

    @Test
    public void testBuild() throws IllegalAccessException {
        final String appName = getClass().getSimpleName();
        final DefaultConfig.Builder builder = new DefaultConfig.Builder(appName, "app.properties");
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
        final DefaultConfig.Builder builder = new DefaultConfig.Builder(appName, "test.properties");
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
