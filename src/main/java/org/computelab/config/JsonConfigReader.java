package org.computelab.config;

import static com.google.common.base.Preconditions.checkNotNull;

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
        checkNotNull(json);
        this.json = json;
    }

    @Override
    JsonElement getVal(final String key) {
        final Queue<String> keys = splitKey(key);
        final JsonElement jsonElement = search(keys, json);
        // Key does not exist
        if (!keys.isEmpty()) {
            return null;
        }
        if (jsonElement == null) {
            return null;
        }
        // Cannot call getAsString() on JsonNull
        // Thus JsonNull is treated as null
        if (jsonElement.isJsonNull()) {
            return null;
        }
        return jsonElement;
    }

    /**
     * Recursively searches the JSON object until the keys are exhausted,
     * or the key does not exist, or a terminal JSON element is found.
     */
    private JsonElement search(final Queue<String> keys, final JsonObject jsonObject) {
        if (keys.isEmpty()) {
            return jsonObject;
        }
        final String key = keys.poll();
        final JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null) {
            return null;
        }
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
