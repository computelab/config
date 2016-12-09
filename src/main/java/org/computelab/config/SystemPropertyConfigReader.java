package org.computelab.config;

final class SystemPropertyConfigReader extends AbstractConfigReader<String> {

    @Override
    String getVal(final String key) {
        return System.getProperty(key);
    }
}
