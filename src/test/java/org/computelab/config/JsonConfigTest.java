package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class JsonConfigTest {

    private String json;

    @Before
    public void before() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/test.json");
                Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader)) {
            json = String.join("\n", bufferedReader.lines().collect(Collectors.toList()));
        }
    }

    @Test(expected = NullPointerException.class)
    public void constructorJsonCannotBeNull() {
        new JsonConfig("jsonConfig", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructorJsonCannotBeEmpty() {
        new JsonConfig("jsonConfig", "");
    }

    @Test(expected = InvalidJsonException.class)
    public void constructorJsonMustBeJson() {
        new JsonConfig("jsonConfig", "not a json");
    }

    @Test(expected = UnexpectedJsonTypeException.class)
    public void constructorJsonMustBeJsonObject() {
        new JsonConfig("jsonConfig", "[1, 2]");
    }

    @Test
    public void testHas() {
        JsonConfig config = new JsonConfig("jsonConfig", json);
        assertTrue(config.has("sp.provider"));
        assertFalse(config.has("sp.user"));
    }

    @Test
    public void testGet() {
        JsonConfig config = new JsonConfig("jsonConfig", json);
        assertEquals(123, config.getAsInt("sp.provider.id"));
    }

    @Test(expected=ConfigEntryMissingException.class)
    public void testGetMissingKey() {
        JsonConfig config = new JsonConfig("jsonConfig", json);
        config.get("sp.user.id");
    }

    @Test
    public void testGetAsList() {
        JsonConfig config = new JsonConfig("jsonConfig", json);
        List<String> urls = config.getAsList("sp.provider.urls");
        assertNotNull(urls);
        assertEquals(2, urls.size());
        assertEquals("url1", urls.get(0));
        assertEquals("url2", urls.get(1));
    }

    @Test(expected=UnsupportedOperationException.class)
    public void listReturnedByGetAsListIsImmutable() {
        JsonConfig config = new JsonConfig("jsonConfig", json);
        List<String> urls = config.getAsList("sp.provider.urls");
        urls.set(0, "url");
    }
}
