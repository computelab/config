package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Parses a regex delimited value list.
 */
abstract class AbstractListConfig extends AbstractConfig {

    private static final String DEFAULT_SPLIT_REGEX = "\\s*,\\s*";

    private final Pattern splitRegex;

    AbstractListConfig(final String name) {
        super(name);
        this.splitRegex = Pattern.compile(DEFAULT_SPLIT_REGEX);
    }

    AbstractListConfig(final String name, final String splitRegex) {
        super(name);
        checkNotNull(splitRegex);
        checkArgument(!splitRegex.isEmpty());
        this.splitRegex = Pattern.compile(splitRegex);
    }

    @Override
    public final List<String> getAsList(String key) {
        return Collections.unmodifiableList(Arrays.asList(splitRegex.split(get(key))));
    }
}
