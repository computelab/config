package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class StackedConfig extends AbstractConfig {

    private final Logger logger = LoggerFactory.getLogger(StackedConfig.class);

    private final List<Config> configs;

    StackedConfig(final String name, final List<Config> configs) {
        super(name);
        checkNotNull(configs);
        checkArgument(configs.size() > 0);
        this.configs = Collections.unmodifiableList(configs);
        logger.info("Stacking configs [" + String.join(", ",
                this.configs.stream().map(
                        config -> config.name()
                ).collect(Collectors.toList())) + "]");
    }

    @Override
    public boolean has(final String key) {
        final Config config = search(key);
        return config != null;
    }

    @Override
    public String get(final String key) {
        final Config config = search(key);
        if (config != null) {
            return config.get(key);
        }
        throw new ConfigEntryMissingException(key);
    }

    @Override
    public List<String> getAsList(final String key) {
        final Config config = search(key);
        if (config != null) {
            return config.getAsList(key);
        }
        throw new ConfigEntryMissingException(key);
    }

    private Config search(String key) {
        for (final Config config : configs) {
            if (config.has(key)) {
                logger.info("Using " + config.name() + " for key " + key + ".");
                return config;
            }
        }
        logger.warn("Key " + key + " is not defined in any of the configs.");
        return null;
    }
}