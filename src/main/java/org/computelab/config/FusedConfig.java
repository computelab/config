package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;

final class FusedConfig extends AbstractConfig {

    private final List<Config> configs;

    FusedConfig(final String name, final List<Config> configs) {
        super(name);
        checkNotNull(configs);
        checkArgument(configs.size() > 0);
        this.configs = Collections.unmodifiableList(configs);
    }

    @Override
    public boolean has(String key) {
        final Config config = search(key);
        return config != null;
    }

    @Override
    public String get(String key) {
        final Config config = search(key);
        if (config != null) {
            return config.get(key);
        }
        throw new ConfigEntryMissingException(key);
    }

    @Override
    public List<String> getAsList(String key) {
        final Config config = search(key);
        if (config != null) {
            return config.getAsList(key);
        }
        throw new ConfigEntryMissingException(key);
    }

    private Config search(String key) {
        for (final Config config : configs) {
            if (config.has(key)) {
                return config;
            }
        }
        return null;
    }
}
