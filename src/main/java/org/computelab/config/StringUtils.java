package org.computelab.config;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.stream.Collectors;

final class StringUtils {

    /**
     * Trims a a list of strings. For each string value in the list,
     * whitespace are trimmed. Then empty strings are removed. The list
     * returned is a new list.
     */
    static List<String> trim(List<String> vals) {
        checkNotNull(vals);
        return vals.stream()
                .map(val -> val.trim())
                .filter(val -> val.length() > 0)
                .collect(Collectors.toList());
    }

    private StringUtils() {}
}
