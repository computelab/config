package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * The application stack or the environment. Typical examples include
 * production, staging, development.
 */
public class AppStack {

    private final String name;

    /**
     * Creates an app stack by name.
     *
     * @param name the name of the app stack
     */
    public AppStack(final String name) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        this.name = name;
    }

    /**
     * The name of this application stack.
     */
    public String name() {
        return name;
    }
}
