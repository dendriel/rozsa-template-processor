package com.vrozsa;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ContextHolder {
    private final Map<String, Object> context;

    // Context created via SET token.
    private final Map<String, Object> customContext;

    private ContextHolder() {
        this(new HashMap<>(), new HashMap<>());
    }

    private ContextHolder(Map<String, Object> values) {
        this(values, new HashMap<>());
    }

    private ContextHolder(Map<String, Object> values, Map<String, Object> customContext) {
        context = values;
        this.customContext = customContext;
    }

    public static ContextHolder create() {
        return new ContextHolder();
    }

    public static ContextHolder create(Map<String, Object> values) {
        return new ContextHolder(values);
    }

    public static ContextHolder from(ContextHolder context) {
        return new ContextHolder(context.context, context.customContext);
    }

    public ContextHolder add(String key, Object value) {
        context.put(key, value);
        return this;
    }

    public ContextHolder addCustom(String key, Object value) {
        customContext.put(key, value);
        return this;
    }

    public Optional<Object> get(String key) {
        if (context.containsKey(key)) {
            return Optional.ofNullable(context.get(key));
        }
        else if (customContext.containsKey(key)) {
            return Optional.ofNullable(customContext.get(key));
        }

        return Optional.empty();
    }
}
