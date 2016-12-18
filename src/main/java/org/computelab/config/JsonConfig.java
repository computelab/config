package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

final class JsonConfig extends AbstractConfig {

    private final JsonParser jsonParser = new JsonParser();
    private final Gson gson = new Gson();
    private final JsonConfigReader configReader;

    JsonConfig(final String name, final String json) {
        super(name);
        checkNotNull(json, "JSON string must not be null.");
        checkArgument(!json.isEmpty(), "JSON string must not be empty.");
        try {
            final JsonObject jsonObj = jsonParser.parse(json).getAsJsonObject();
            configReader = new JsonConfigReader(jsonObj);
        } catch (final JsonParseException ex) {
            throw new InvalidJsonException(json, ex);
        } catch (final IllegalStateException ex) {
            throw new UnexpectedJsonTypeException(json, ex);
        }
    }

    @Override
    public boolean has(final String key) {
        return configReader.has(key);
    }

    @Override
    public String get(final String key) {
        return configReader.get(key).value().getAsString();
    }

    @Override
    public List<String> getAsList(final String key) {
        return Collections.unmodifiableList(StringUtils.trim(Arrays.asList(
                gson.fromJson(configReader.get(key).value(), String[].class))));
    }
}
