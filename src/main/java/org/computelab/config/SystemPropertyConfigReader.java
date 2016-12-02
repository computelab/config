package org.computelab.config;

final class SystemPropertyConfigReader extends AbstractConfigReader<String> {

    @Override
    boolean hasKey(final String key) {
        return getVal(key) != null;
    }

    @Override
    String getVal(final String key) {
        return System.getProperty(key);
    }
}
