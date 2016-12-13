package org.computelab.config;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Properties;

final class PropertiesConfigReader extends AbstractConfigReader<String> {

    private final Properties properties;

    PropertiesConfigReader(final Properties properties) {
        checkNotNull(properties);
        // Make a defensive copy
        this.properties = new Properties();
        for (String key : properties.stringPropertyNames()) {
            this.properties.setProperty(key, properties.getProperty(key));
        }
    }

    @Override
    String getVal(final String key) {
        return properties.getProperty(key);
    }
}
