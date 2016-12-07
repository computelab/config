package org.computelab.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StringUtilsTest {

    @Test
    public void testTrim() {
        List<String> original = Arrays.asList("", " a ");
        List<String> trimmed = StringUtils.trim(original);
        assertNotNull(trimmed);
        assertEquals(1, trimmed.size());
        assertEquals("a", trimmed.get(0));
    }

    @Test
    public void testTrimListReturnedIsNewCopy() {
        List<String> original = Arrays.asList("", " a ");
        List<String> trimmed = StringUtils.trim(original);
        assertNotNull(trimmed);
        original.set(1, "b");
        assertEquals(1, trimmed.size());
        assertEquals("a", trimmed.get(0));
    }

    @Test(expected=NullPointerException.class)
    public void testTrimListCannotBeNull() {
        StringUtils.trim(null);
    }
}
