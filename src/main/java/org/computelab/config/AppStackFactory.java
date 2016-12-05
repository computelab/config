package org.computelab.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public final class AppStackFactory {

    public static AppStack fromName(final String name) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        final String lowercaseName = name.toLowerCase();
        switch(lowercaseName) {
        case PRD:
        case PRODUCTION:
            return PRD_STACK;
        case STG:
        case STAGING:
            return STG_STACK;
        case INT:
        case INTEGRATION:
            return INT_STACK;
        case DEV:
        case DEVELOPMENT:
            return DEV_STACK;
        default:
            return create(name);
        }
    }

    private static final String PRODUCTION = "production";
    private static final String STAGING = "staging";
    private static final String INTEGRATION = "integration";
    private static final String DEVELOPMENT = "development";

    private static final String PRD = "prd";
    private static final String STG = "stg";
    private static final String INT = "int";
    private static final String DEV = "dev";

    private static final AppStack PRD_STACK = create(PRODUCTION);
    private static final AppStack STG_STACK = create(STAGING);
    private static final AppStack INT_STACK = create(INTEGRATION);
    private static final AppStack DEV_STACK = create(DEVELOPMENT);

    private static AppStack create(final String name) {
        checkNotNull(name);
        checkArgument(!name.isEmpty());
        return new AppStack() {
            @Override
            public String name() {
                return name;
            }
        };
    }

    private AppStackFactory() {}
}
