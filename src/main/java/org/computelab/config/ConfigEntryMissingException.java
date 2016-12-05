package org.computelab.config;

/**
 * Exception when an expected entry is missing the config.
 */
@SuppressWarnings("serial")
public final class ConfigEntryMissingException extends RuntimeException{

    private final String key;

    /**
     * Creates an exception of missing config entry.
     *
     * @param key the key for which the config entry is missing
     */
    public ConfigEntryMissingException(final String key) {
        super("Config missing for " + key + ".");
        this.key = key;
    }

    /**
     * The key for which the config entry is missing.
     */
    public String key() {
        return key;
    }
}
