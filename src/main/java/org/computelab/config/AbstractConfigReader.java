package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

abstract class AbstractConfigReader<T> implements ConfigReader<String, T>  {

    @Override
    public ConfigEntry<String, T> get(final String key) {
        checkNotNull(key);
        checkArgument(!key.isEmpty());
        if (!hasKey(key)) {
            throw new ConfigEntryMissingException(key);
        }
        return new ConfigEntry<String, T>() {
            @Override
            public String key() {
                return key;
            }
            @Override
            public T value() {
                return getVal(key);
            }
        };
    }

    @Override
    public boolean has(final String key) {
        checkNotNull(key);
        return hasKey(key);
    }

    /**
     * 
     * @param key
     * @return
     */
    abstract boolean hasKey(String key);

    /**
     * The value for the specified key; or null if not exist
     */
    abstract T getVal(String key);
}
