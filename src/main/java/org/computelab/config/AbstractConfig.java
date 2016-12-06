package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Takes care of setting the name and the <code>getAs...()</code>
 * parsings.
 */
abstract class AbstractConfig implements Config {

    private final String name;

    AbstractConfig(final String name) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
    }

    @Override
    public final String name() {
        return name;
    }

    @Override
    public final boolean getAsBoolean(String key) {
        return getAsType(key, Boolean::parseBoolean);
    }

    @Override
    public final int getAsInt(String key) {
        return getAsType(key, Integer::parseInt);
    }

    @Override
    public final long getAsLong(String key) {
        return getAsType(key, Long::parseLong);
    }

    @FunctionalInterface
    private static interface Parse<T> {
        T apply(String value);
    }

    private <T> T getAsType(String key, Parse<T> parse) {
        checkNotNull(key);
        checkArgument(!key.isEmpty());
        return parse.apply(get(key));
    }
}
