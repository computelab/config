package org.computelab.config;

import java.util.List;

public interface Config {

    /**
     * The name of the configuration.
     */
    String name();

    /**
     * Whether the config entry on the specified key exists.
     * For normal logic flow, check this instead of catching
     * <code>ConfigEntryMissingException</code>.
     */
    boolean has(String key);

    /**
     * Gets the value for the specified key. By default,
     * values are treated as strings.
     *
     * @throws ConfigEntryMissingException if the config entry does not exist
     *             for the specified key
     */
    String get(String key);

    /**
     * Gets the config value as an immutable list.
     */
    List<String> getAsList(String key);

    /**
     * Gets the config value as a boolean.
     */
    boolean getAsBoolean(String key);

    /**
     * Gets the config value as an integer.
     */
    int getAsInt(String key);

    /**
     * Gets the config value as a long integer.
     */
    long getAsLong(String key);
}
