package org.computelab.config;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Properties;

final class PropertiesConfig extends AbstractListConfig {

    private final ConfigReader<String, String> configReader;

    PropertiesConfig(final String name, final Properties properties) {
        super(name);
        checkNotNull(properties);
        configReader = new PropertiesConfigReader(properties);
    }

    PropertiesConfig(final String name, final Properties properties,
            final String splitRegex) {
        super(name, splitRegex);
        checkNotNull(properties);
        configReader = new PropertiesConfigReader(properties);
    }

    @Override
    public boolean has(String key) {
        return configReader.has(key);
    }

    @Override
    public String get(String key) {
        return configReader.get(key).value();
    }
}
