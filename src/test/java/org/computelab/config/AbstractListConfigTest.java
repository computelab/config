package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class AbstractListConfigTest {

    @Test
    public void testDefaultSplitRegex() {
        List<String> vals = mockConfig("a, b, c").getAsList("anyKey");
        verify(vals);
        vals = mockConfig("a,b,c").getAsList("anyKey");
        verify(vals);
        vals = mockConfig("a\t,\tb\r\n,\nc").getAsList("anyKey");
        verify(vals);
    }

    @Test
    public void testWhitespacesAreTrimmed() {
        List<String> vals = mockConfig(" a, b, c ").getAsList("anyKey");
        verify(vals);
    }

    @Test
    public void testBlankValuesAreFiltered() {
        List<String> vals = mockConfig(", a, b, c, ").getAsList("anyKey");
        verify(vals);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testListInImmutable() {
        Config config = mockConfig("a, b, c");
        List<String> vals = config.getAsList("anyKey");
        vals.add("d");
    }

    @Test
    public void testCustomSplitRegex() {
        Config config = mockConfig("\\.", "a.b.c");
        List<String> vals = config.getAsList("anyKey");
        assertNotNull(vals);
        assertEquals(3, vals.size());
        assertEquals("a", vals.get(0));
        assertEquals("b", vals.get(1));
        assertEquals("c", vals.get(2));
    }

    @Test(expected=NullPointerException.class)
    public void testCustomSplitRegexCannotBeNull() {
        mockConfig(null, "a.b.c");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testSplitRegexCannotBeEmpty() {
        mockConfig("", "a.b.c");
    }

    private void verify(List<String> vals) {
        assertNotNull(vals);
        assertEquals(3, vals.size());
        assertEquals("a", vals.get(0));
        assertEquals("b", vals.get(1));
        assertEquals("c", vals.get(2));
    }

    private Config mockConfig(String get) {
        return new AbstractListConfig("MockConfig") {
            @Override
            public boolean has(String key) {
                return get != null;
            }
            @Override
            public String get(String key) {
                return get;
            }
        };
    }

    private Config mockConfig(String splitRegex, String get) {
        return new AbstractListConfig("MockConfig", splitRegex) {
            @Override
            public boolean has(String key) {
                return get != null;
            }
            @Override
            public String get(String key) {
                return get;
            }
        };
    }
}
