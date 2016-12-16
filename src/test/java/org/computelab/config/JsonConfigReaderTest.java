package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonConfigReaderTest {

    private final JsonParser parser = new JsonParser();
    private JsonObject json;

    @Before
    public void before() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/test.json");
                Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            json = parser.parse(reader).getAsJsonObject();
        }
    }

    @Test(expected=NullPointerException.class)
    public void constructorJsonCannotBeNull() {
        new JsonConfigReader(null);
    }

    @Test
    public void testHas() {
        ConfigReader<String, JsonElement> reader = new JsonConfigReader(
                parser.parse("{\"key\": \"val\"}").getAsJsonObject());
        assertTrue(reader.has("key"));
        assertFalse(reader.has("anotherKey"));
    }

    @Test
    public void testGetVal() {
        JsonConfigReader reader = new JsonConfigReader(
                parser.parse("{\"key\": \"val\"}").getAsJsonObject());
        assertEquals("val", reader.getVal("key").getAsString());
    }

    @Test
    public void testGetValReturnsNullOnMissingKey() {
        JsonConfigReader reader = new JsonConfigReader(
                parser.parse("{\"key\": \"val\"}").getAsJsonObject());
        assertNull(reader.getVal("AnotherKey"));
    }

    @Test
    public void testGetJsonNull() {
        JsonConfigReader reader = new JsonConfigReader(
                parser.parse("{\"key\": null}").getAsJsonObject());
        assertNull(reader.getVal("key"));
        assertFalse(reader.has("key"));
    }

    @Test
    public void testKeyIsParsedAsJsonPath() {
        JsonConfigReader reader = new JsonConfigReader(json);
        assertEquals("567", reader.getVal("sp.researcher.id").getAsString());
    }

    @Test
    public void testPartialJsonPath() {
        JsonConfigReader reader = new JsonConfigReader(json);
        assertNotNull(reader.getVal("sp.researcher"));
        assertTrue(reader.getVal("sp.researcher").isJsonObject());
        JsonElement researcher = reader.getVal("sp.researcher");
        assertEquals("567", researcher.getAsJsonObject().get("id").getAsString());
    }

    @Test
    public void testIncorrectJsonPath() {
        JsonConfigReader reader = new JsonConfigReader(json);
        assertNull(reader.getVal("sp.researcher.name"));
    }

    @Test
    public void testJsonArray() {
        JsonConfigReader reader = new JsonConfigReader(json);
        assertTrue(reader.getVal("sp.provider.urls").isJsonArray());
        assertEquals(parser.parse("[\"url1\",\"url2\", \" \"]"),
                reader.getVal("sp.provider.urls"));
    }
}
