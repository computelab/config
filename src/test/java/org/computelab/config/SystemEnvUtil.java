package org.computelab.config;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

final class SystemEnvUtil {

    // http://stackoverflow.com/questions/318239
    @SuppressWarnings({ "rawtypes", "unchecked" })
    static void setEnv(String key, String val) throws Exception {
        Class[] classes = Collections.class.getDeclaredClasses();
        Map<String, String> env = System.getenv();
        for (Class cl : classes) {
            if ("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                Field field = cl.getDeclaredField("m");
                field.setAccessible(true);
                Object obj = field.get(env);
                Map<String, String> map = (Map<String, String>) obj;
                map.put(key, val);
            }
        }
    }

    private SystemEnvUtil() {}
}
