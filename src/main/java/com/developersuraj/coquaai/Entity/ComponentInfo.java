package com.developersuraj.coquaai.Entity;

import java.util.List;

public class ComponentInfo {

    private final Class<?> targetClass;
    private final ComponentType type;
    private final List<Class<?>> dependencies;

    public ComponentInfo(Class<?> targetClass,
                         ComponentType type,
                         List<Class<?>> dependencies) {
        this.targetClass = targetClass;
        this.type = type;
        this.dependencies = dependencies;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public ComponentType getType() {
        return type;
    }

    public List<Class<?>> getDependencies() {
        return dependencies;
    }

    public String getName() {
        return targetClass.getSimpleName();
    }
}
