package org.computelab.config;

/**
 * Configuration reader that reads a particular type of config entry.
 *
 * @param <K> the type of the config entry's key
 * @param <V> the type of the config entry's value
 */
public interface ConfigReader<K, V> {

    /**
     * Reads a config entry by the specified key. If the entry does not exist,
     * <code>ConfigEntryMissingException</code> is thrown.
     *
     * @param key config entry key
     * @return the config entry for the specified key
     * @throws ConfigEntryMissingException if the entry does not exist
     */
    ConfigEntry<K, V> get(K key);

    /**
     * Whether the key exists. Whether the key has a mapped entry.
     *
     * @param key config entry key
     * @return true if the config entry exists for the specified key;
     *         false otherwise
     */
    boolean has(K key);
}
