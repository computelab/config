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
     */
    ConfigEntry<K, V> get(K key);

    /**
     * Whether the key exists. Whether the key has a mapped entry.
     */
    boolean has(K key);
}
