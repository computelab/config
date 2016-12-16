package org.computelab.config;

/**
 * When the JSON string is not an expected type. The list of JSON types include
 * object, primitive, array, and null.
 */
public class UnexpectedJsonTypeException extends RuntimeException {

    private static final long serialVersionUID = -6557318334015015783L;

    private final String json;

    /**
     * @param json the JSON string causing this exception
     */
    public UnexpectedJsonTypeException(final String json) {
        this.json = json;
    }

    /**
     * @param json the JSON string causing this exception
     * @param cause the exception that causes this exception
     */
    public UnexpectedJsonTypeException(final String json, final Throwable cause) {
        super(cause);
        this.json = json;
    }

    /**
     * @return the JSON string causing this exception
     */
    public String getJson() {
        return json;
    }
}
