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
        this(name, DEFAULT_SPLIT_REGEX);
    }

    AbstractListConfig(final String name, final String splitRegex) {
        super(name);
        checkNotNull(splitRegex, "Split regex must not be null.");
        checkArgument(!splitRegex.isEmpty(), "Split regex must not be empty.");
        this.splitRegex = Pattern.compile(splitRegex);
    }

    @Override
    public final List<String> getAsList(String key) {
        return Collections.unmodifiableList(StringUtils.trim(
                Arrays.asList(splitRegex.split(get(key)))));
    }
}
