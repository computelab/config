package org.computelab.config;

/**
 * The application stack (or the deployed environment). Typical examples include
 * production, staging, integration, and development.
 */
public interface AppStack {

    /**
     * @return the name of this application stack
     */
    String name();
}
