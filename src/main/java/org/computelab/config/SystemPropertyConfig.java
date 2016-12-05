package org.computelab.config;

final class SystemPropertyConfig extends AbstractListConfig {

    private final ConfigReader<String, String> configReader =
            new SystemPropertyConfigReader();

    SystemPropertyConfig(final String name) {
        super(name);
    }

    SystemPropertyConfig(final String name, final String splitRegex) {
        super(name, splitRegex);
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
