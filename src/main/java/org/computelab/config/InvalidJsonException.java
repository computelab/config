package org.computelab.config;

public class InvalidJsonException extends RuntimeException {

    private static final long serialVersionUID = 2290965762420739021L;

    private final String json;

    /**
     * @param json the JSON string causing this exception
     */
    public InvalidJsonException(final String json) {
        this.json = json;
    }

    /**
     * @param json the JSON string causing this exception
     * @param cause the exception that causes this exception
     */
    public InvalidJsonException(final String json, final Throwable cause) {
        super(cause);
        this.json = json;
    }

    /**
     * @param json the JSON string causing this exception
     */
    public String getJson() {
        return json;
    }
}
