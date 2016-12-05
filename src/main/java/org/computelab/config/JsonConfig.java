package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

final class JsonConfig extends AbstractConfig {

    private final JsonParser jsonParser = new JsonParser();
    private final Gson gson = new Gson();
    private final JsonConfigReader configReader;

    JsonConfig(final String name, final String json) {
        super(name);
        checkNotNull(json);
        checkArgument(!json.isEmpty());
        final JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        configReader = new JsonConfigReader(jsonObject);
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
        return Collections.unmodifiableList(Arrays.asList(
                gson.fromJson(configReader.get(key).value(), String[].class)));
    }
}
