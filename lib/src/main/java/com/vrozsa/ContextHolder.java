package com.vrozsa;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContextHolder {

    private final Map<String, Object> context;

    private ContextHolder() {
        context = new HashMap<>();
    }

    public static ContextHolder create() {
        return new ContextHolder();
    }

    public ContextHolder add(String key, Object value) {
        context.put(key, value);
        return this;
    }

    // TODO: handle objects with nodes.
    public Optional<Object> get(String key) {
        if (!context.containsKey(key)) {
            return Optional.empty();
        }
        return Optional.ofNullable(context.get(key));
    }
}
