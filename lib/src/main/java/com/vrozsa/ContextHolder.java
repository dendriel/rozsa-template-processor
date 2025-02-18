package com.vrozsa;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContextHolder {
    private final Map<String, Object> context;

    // Context created via SET token.
    private final Map<String, Object> customContext;

    private ContextHolder() {
        context = new HashMap<>();
        customContext = new HashMap<>();
    }

    public static ContextHolder create() {
        return new ContextHolder();
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
