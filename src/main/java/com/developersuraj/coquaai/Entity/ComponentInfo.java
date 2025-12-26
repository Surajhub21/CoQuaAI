package com.developersuraj.coquaai.Entity;

import java.util.List;

public class ComponentInfo {

    private final String name;
    private final ComponentType type;
    private final List<Class<?>> dependencies;

    public ComponentInfo(
            String name,
            ComponentType type,
            List<Class<?>> dependencies) {
        this.name = name;
        this.type = type;
        this.dependencies = dependencies;
    }

    public String getName() {
        return name;
    }

    public ComponentType getType() {
        return type;
    }

    public List<Class<?>> getDependencies() {
        return dependencies;
    }
}
