package org.computelab.config;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

final class JsonConfigReader extends AbstractConfigReader<JsonElement> {

    private final Pattern split = Pattern.compile("\\" + ConfigConstants.KEY_DELIMITER);
    private final JsonObject json;

    JsonConfigReader(final JsonObject json) {
        this.json = json;
    }

    @Override
    boolean hasKey(final String key) {
        final Queue<String> keys = splitKey(key);
        final JsonElement jsonElement = search(keys, json);
        return keys.isEmpty() && !jsonElement.isJsonNull();
    }

    @Override
    JsonElement getVal(final String key) {
        return search(splitKey(key), json);
    }

    private JsonElement search(final Queue<String> keys, final JsonObject jsonObject) {
        if (keys.isEmpty()) {
            return jsonObject;
        }
        final String key = keys.poll();
        final JsonElement jsonElement = jsonObject.get(key);
        if (!jsonElement.isJsonObject()) {
            return jsonElement;
        }
        return search(keys, jsonElement.getAsJsonObject());
    }

    private Queue<String> splitKey(final String key) {
        final Queue<String> keys = new ArrayDeque<>();
        Arrays.asList(split.split(key)).forEach(k -> keys.offer(k));
        return keys;
    }
}
