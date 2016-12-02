package org.computelab.config;

final class SystemEnvConfig extends AbstractListConfig {

    private final ConfigReader<String, String> configReader =
            new SystemEnvConfigReader();

    SystemEnvConfig(final String name, final AppStack appStack) {
        super(name, appStack);
    }

    SystemEnvConfig(final String name, final AppStack appStack,
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
