package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A <code>Config</code> that reads the following sources in order:
 * <ol>
 * <li> System properties, which are usually passed as -D parameters on command-line.
 * <li> Environment variables.
 * <li> The properties file in the app's home. The app's home is the hidden folder
 *      "~/.[app]" in the user's home. This file is optional.
 * <li> The properties file as a source code resource. Optional.
 * </ol>
 */
public final class DefaultConfig {

    /**
     * Default name of the config file. The config file here refers to the properties
     * file in the app's home and in the source code. These files are optional.
     */
    public static final String DEFAULT_CONFIG_FILE = "app.properties";

    /**
     * Creates a default config with the specified app name. The app name will be
     * used to located the app's home directory. The app's home is the hidden folder
     * "~/.[app]" in the user's home. For example, if the app name is "foo", the app's
     * home will be "~/.foo" in the user's home directory. This directory will be
     * searched for the default config file "app.properties".
     *
     * @param appName the name of the app that is used to locate the app's home
     * @return the created default config instance
     */
    public static Config create(final String appName) {
        return create(appName, DEFAULT_CONFIG_FILE);
    }

    /**
     * Creates a default config with the specified app name and the specified config
     * file name. The app name will be used to located the app's home directory. The
     * app's home is the hidden folder "~/.[app]" in the user's home. For example,
     * if the app name is "foo", the app's home will be "~/.foo" in the user's home
     * directory. This directory will be searched for the specified properties file.
     *
     * @param appName the name of the app that is used to locate the app's home
     * @param configFile the name of the config file
     * @return the created default config instance
     */
    public static Config create(final String appName, final String configFile) {
        checkNotNull(appName, "App name must not be null.");
        checkArgument(!appName.isEmpty(), "App name must not be empty.");
        checkNotNull(configFile, "Config file name must not be null.");
        checkArgument(!configFile.isEmpty(), "Config file name must not be empty.");
        return new Builder(appName, configFile).build();
    }

    static class Builder {

        private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

        private final Logger logger = LoggerFactory.getLogger(DefaultConfig.class);

        private final String appName;
        private final String configFile;

        Builder(final String appName, final String configFile) {
            this.appName = appName;
            this.configFile = configFile;
        }

        Config build() {
            final ConfigBuilder builder = new ConfigBuilder();
            builder.addSystemPropertyConfig();
            builder.addSystemEnvConfig();
            final Properties homeProps = getFromHome();
            if (homeProps != null) {
                builder.addPropertiesConfig(homeProps);
            }
            final Properties srcProps = getFromSource();
            if (srcProps != null) {
                builder.addPropertiesConfig(srcProps);
            }
            return builder.build();
        }

        private Properties getFromHome() {
            final String userHome = System.getProperty("user.home");
            final String appHome = "." + appName;
            final String configFilePath = String.join(File.separator, userHome, appHome, configFile);
            try (final FileInputStream inputStream = new FileInputStream(configFilePath)) {
                return getProperties(inputStream);
            } catch (final FileNotFoundException ex) {
                logger.warn("Missing config file " + configFilePath + ". Skipping it...");
                return null;
            } catch (final IOException ex) {
                logger.warn("Error reading config file " + configFile + ". Skipping it...", ex);
                return null;
            }
        }

        private Properties getFromSource() {
            try (final InputStream inputStream = getClass().getResourceAsStream("/" + configFile)) {
                if (inputStream == null) {
                    logger.warn("Missing resource " + configFile + ". Skipping it...");
                    return null;
                }
                return getProperties(inputStream);
            } catch (final IOException ex) {
                logger.warn("Error reading resource " + configFile + ". Skipping it...", ex);
                return null;
            }
        }

        private Properties getProperties(final InputStream inputStream) throws IOException {
            try (final InputStreamReader reader = new InputStreamReader(inputStream, DEFAULT_CHARSET)) {
                final Properties props = new Properties();
                props.load(reader);
                return props;
            }
        }
    }
}
