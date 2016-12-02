package org.computelab.config;

final class SystemPropertyConfig extends AbstractListConfig {

    private final ConfigReader<String, String> configReader =
            new SystemPropertyConfigReader();

    SystemPropertyConfig(final String name, final AppStack appStack) {
        super(name, appStack);
    }

    SystemPropertyConfig(final String name, final AppStack appStack,
            final String splitRegex) {
        super(name, appStack, splitRegex);
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
