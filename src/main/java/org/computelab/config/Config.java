package org.computelab.config;

import java.util.List;

public interface Config {

    /**
     * @return the name of the configuration.
     */
    String name();

    /**
     * Whether the config entry of the specified key exists.
     * For normal logic flow, check this instead of catching
     * <code>ConfigEntryMissingException</code>.
     *
     * @param key config entry key
     * @return true if the config entry exists for the specified key;
     *         false otherwise
     */
    boolean has(String key);

    /**
     * Gets the value for the specified key. By default,
     * values are treated as strings.
     *
     * @param key config entry key
     * @return the value for the specified key
     * @throws ConfigEntryMissingException if the config entry does not exist
     *             for the specified key
     */
    String get(String key);

    /**
     * Gets the config value as an immutable list. Leading and trailing
     * whitespace will be trimmed off the values. Blank values will be
     * filtered from the list.
     *
     * @param key config entry key
     * @return the list of values for the specified key
     */
    List<String> getAsList(String key);

    /**
     * Gets the config value as a boolean. Note that this is consistent
     * with <code>Boolean.parseBoolean(String)</code> in that only
     * "true" ignoring case parses as <code>true</code>. Everything
     * else parses as <code>false</code>.
     *
     * @param key config entry key
     * @return the config value as a boolean
     */
    boolean getAsBoolean(String key);

    /**
     * Gets the config value as an integer.
     *
     * @param key config entry key
     * @return the config value as an integer
     * @throws NumberFormatException if the value is not a valid integer
     */
    int getAsInt(String key);

    /**
     * Gets the config value as a long integer.
     *
     * @param key config entry key
     * @return the config value as a long integer
     * @throws NumberFormatException if the value is not a valid integer
     */
    long getAsLong(String key);
}
