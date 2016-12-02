package org.computelab.config;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public final class ConfigBuilder {

    private final AppStack stack;
    private final List<Config> configs = new ArrayList<>();

    public ConfigBuilder(final AppStack stack) {
        checkNotNull(stack);
        this.stack = stack;
    }

    public ConfigBuilder addJsonConfig(final String json) {
        final String configName = JsonConfig.class.getSimpleName();
        configs.add(new JsonConfig(configName, stack, json));
        return this;
    }

    public ConfigBuilder addJsonConfig(final String configName, final String json) {
        configs.add(new JsonConfig(configName, stack, json));
        return this;
    }

    public ConfigBuilder addPropertiesConfig(final Properties properties) {
        final String configName = PropertiesConfig.class.getSimpleName();
        configs.add(new PropertiesConfig(configName, stack, properties));
        return this;
    }

    public ConfigBuilder addPropertiesConfig(final String configName,
            final Properties properties) {
        configs.add(new PropertiesConfig(configName, stack, properties));
        return this;
    }

    public ConfigBuilder addPropertiesConfig(final Properties properties,
            final String splitRegex) {
        final String configName = PropertiesConfig.class.getSimpleName();
        configs.add(new PropertiesConfig(configName, stack, properties, splitRegex));
        return this;
    }

    public ConfigBuilder addPropertiesConfig(final String configName,
            final Properties properties, final String splitRegex) {
        configs.add(new PropertiesConfig(configName, stack, properties, splitRegex));
        return this;
    }

    public ConfigBuilder addSystemEnvConfig() {
        final String configName = SystemEnvConfig.class.getSimpleName();
        configs.add(new SystemEnvConfig(configName, stack));
        return this;
    }

    public ConfigBuilder addSystemEnvConfig(final String configName) {
        configs.add(new SystemEnvConfig(configName, stack));
        return this;
    }

    public ConfigBuilder addSystemEnvConfig(final String configName,
            final String splitRegex) {
        configs.add(new SystemEnvConfig(configName, stack, splitRegex));
        return this;
    }

    public ConfigBuilder addSystemPropertyConfig() {
        final String configName = SystemPropertyConfig.class.getSimpleName();
        configs.add(new SystemPropertyConfig(configName, stack));
        return this;
    }

    public ConfigBuilder addSystemPropertyConfig(final String configName) {
        configs.add(new SystemPropertyConfig(configName, stack));
        return this;
    }

    public ConfigBuilder addSystemPropertyConfig(final String configName,
            final String splitRegex) {
        configs.add(new SystemPropertyConfig(configName, stack, splitRegex));
        return this;
    }

    public Config build() {
        return new FusedConfig(FusedConfig.class.getSimpleName(), stack, configs);
    }

    public Config build(final String configName) {
        return new FusedConfig(configName, stack, configs);
    }
}
