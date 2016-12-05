package org.computelab.config;

import static org.computelab.config.ConfigConstants.ENV_KEY_DELIMITER;
import static org.computelab.config.ConfigConstants.KEY_DELIMITER;

final class SystemEnvConfigReader extends AbstractConfigReader<String> {

    @Override
    boolean hasKey(final String key) {
        return getVal(key) != null;
    }

    @Override
    String getVal(final String key) {
        return System.getenv(convert(key));
    }

    private String convert(final String key) {
        return key.replace(KEY_DELIMITER, ENV_KEY_DELIMITER).toUpperCase();
    }
}
