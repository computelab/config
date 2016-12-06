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
     * Gets the config value as a boolean. Note that this is consistent
     * with <code>Boolean.parseBoolean(String)</code> in that only
     * "true" ignoring case parses as <code>true</code>. Everything
     * else parses as <code>false</code>.
     */
    boolean getAsBoolean(String key);

    /**
     * Gets the config value as an integer.
     *
     * @throws NumberFormatException if the value is not a valid integer
     */
    int getAsInt(String key);

    /**
     * Gets the config value as a long integer.
     *
     * @throws NumberFormatException if the value is not a valid integer
     */
    long getAsLong(String key);
}
