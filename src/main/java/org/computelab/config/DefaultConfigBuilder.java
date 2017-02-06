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
 * Builds a <code>Config</code> that reads the following sources in order:
 * <ol>
 * <li> System properties which are usually passed on command-line as -D parameters.
 * <li> Environment variables.
 * <li> User's home directory, optional.
 * <li> Resource in the source code, optional.
 * </ol>
 */
public final class DefaultConfigBuilder {

    public static final String DEFAULT_CONFIG_FILE = "app.properties";

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final Logger logger = LoggerFactory.getLogger(DefaultConfigBuilder.class);

    private final String appName;
    private final String configFile;

    public DefaultConfigBuilder(final String appName) {
        this(appName, DEFAULT_CONFIG_FILE);
    }

    public DefaultConfigBuilder(final String appName, final String configFile) {
        checkNotNull(appName, "App name must not be null.");
        checkArgument(!appName.isEmpty(), "App name must not be empty.");
        checkNotNull(configFile, "Config file name must not be null.");
        checkArgument(!configFile.isEmpty(), "Config file name must not be empty.");
        this.appName = appName;
        this.configFile = configFile;
    }

    public Config build() {
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
        final String configFilePath = String.join(File.separator,  userHome, appHome, configFile);
        try (final FileInputStream inputStream = new FileInputStream(configFilePath);) {
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
