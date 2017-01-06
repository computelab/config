package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A chain of configs. When reading value for a key, the first config
 * in the chain is consulted first. If the key exists, the value is
 * returned; otherwise the config next in the chain is consulted. So
 * on and so forth until the end of the chain is reached.
 */
final class ConfigChain extends AbstractConfig {

    private final Logger logger = LoggerFactory.getLogger(ConfigChain.class);

    private final List<Config> configs;

    ConfigChain(final String name, final List<Config> configs) {
        super(name);
        checkNotNull(configs);
        checkArgument(configs.size() > 0);
        this.configs = configs;
        logger.info("[" + String.join(", ",
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
