package org.computelab.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class ConfigBuilder {

    private final List<Config> configs = new ArrayList<>();

    public ConfigBuilder addJsonConfig(final String json) {
        return addJsonConfig(JsonConfig.class.getSimpleName(), json);
    }

    public ConfigBuilder addJsonConfig(final String configName, final String json) {
        configs.add(new JsonConfig(configName, json));
        return this;
    }

    public ConfigBuilder addPropertiesConfig(final Properties properties) {
        return addPropertiesConfig(PropertiesConfig.class.getSimpleName(), properties);
    }

    public ConfigBuilder addPropertiesConfig(final String configName, final Properties properties) {
        configs.add(new PropertiesConfig(configName, properties));
        return this;
    }

    public ConfigBuilder addPropertiesConfig(
            final String configName,
            final Properties properties,
            final String splitRegex) {
        configs.add(new PropertiesConfig(configName, properties, splitRegex));
        return this;
    }

    public ConfigBuilder addSystemEnvConfig() {
        return addSystemEnvConfig(SystemEnvConfig.class.getSimpleName());
    }

    public ConfigBuilder addSystemEnvConfig(final String configName) {
        configs.add(new SystemEnvConfig(configName));
        return this;
    }

    public ConfigBuilder addSystemEnvConfig(final String configName, final String splitRegex) {
        configs.add(new SystemEnvConfig(configName, splitRegex));
        return this;
    }

    public ConfigBuilder addSystemPropertyConfig() {
        return addSystemPropertyConfig(SystemPropertyConfig.class.getSimpleName());
    }

    public ConfigBuilder addSystemPropertyConfig(final String configName) {
        configs.add(new SystemPropertyConfig(configName));
        return this;
    }

    public ConfigBuilder addSystemPropertyConfig(final String configName, final String splitRegex) {
        configs.add(new SystemPropertyConfig(configName, splitRegex));
        return this;
    }

    public Config build() {
        return build(StackedConfig.class.getSimpleName());
    }

    public Config build(final String configName) {
        return new StackedConfig(configName, configs);
    }
}
