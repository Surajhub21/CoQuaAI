package com.developersuraj.coquaai.core.analyzer;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.ComponentType;
import com.developersuraj.coquaai.config.CoQuaAIProperties;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Scans the running application context for Controller/Service/Repository beans.
 * Only beans that live inside the host application's own base packages are considered;
 * Spring Boot infrastructure beans, actuator beans, and CoQuaAI's own beans are always excluded.
 */
public class SpringContextScanner {

    private static final Set<String> ALWAYS_EXCLUDED_PREFIXES = Set.of(
            "com.developersuraj.coquaai",
            "org.springframework",
            "org.springdoc",
            "java.",
            "javax.",
            "jakarta.",
            "kotlin."
    );

    private static final String SPRING_DATA_REPOSITORY_MARKER = "org.springframework.data.repository.Repository";

    private final ApplicationContext context;
    private final CoQuaAIProperties properties;

    public SpringContextScanner(ApplicationContext context, CoQuaAIProperties properties) {
        this.context = context;
        this.properties = properties;
    }

    public List<ComponentInfo> scan() {

        List<ComponentInfo> components = new ArrayList<>();
        List<String> basePackages = resolveBasePackages();

        context.getBeansWithAnnotation(RestController.class).values()
                .forEach(bean -> addIfEligible(components, bean, ComponentType.CONTROLLER, basePackages));


        context.getBeansWithAnnotation(Service.class).values()
                .forEach(bean -> addIfEligible(components, bean, ComponentType.SERVICE, basePackages));

        context.getBeansWithAnnotation(Repository.class).values()
                .forEach(bean -> addIfEligible(components, bean, ComponentType.REPOSITORY, basePackages));

        scanSpringDataRepositories(components, basePackages);

        return components;
    }

    private void scanSpringDataRepositories(List<ComponentInfo> components, List<String> basePackages) {

        Set<Class<?>> alreadyAdded = new LinkedHashSet<>();
        for (ComponentInfo component : components) {
            alreadyAdded.add(component.getTargetClass());
        }

        for (String beanName : context.getBeanDefinitionNames()) {

            Class<?> beanType;
            try {
                beanType = context.getType(beanName);
            } catch (Exception ex) {
                continue;
            }

            if (beanType == null || alreadyAdded.contains(beanType) || !implementsSpringDataRepositoryMarker(beanType)) {
                continue;
            }

            if (!isEligible(beanType, basePackages)) {
                continue;
            }

            components.add(new ComponentInfo(beanType, ComponentType.REPOSITORY, List.of()));
            alreadyAdded.add(beanType);
        }
    }

    private boolean implementsSpringDataRepositoryMarker(Class<?> clazz) {

        if (clazz == null || clazz == Object.class) {
            return false;
        }

        for (Class<?> iface : clazz.getInterfaces()) {
            if (SPRING_DATA_REPOSITORY_MARKER.equals(iface.getName()) || implementsSpringDataRepositoryMarker(iface)) {
                return true;
            }
        }

        return implementsSpringDataRepositoryMarker(clazz.getSuperclass());
    }

    private void addIfEligible(List<ComponentInfo> components, Object bean, ComponentType type, List<String> basePackages) {

        Class<?> realClass = getRealClass(bean);

        if (!isEligible(realClass, basePackages)) {
            return;
        }

        components.add(new ComponentInfo(realClass, type, extractConstructorDependencies(realClass)));
    }

    private boolean isEligible(Class<?> clazz, List<String> basePackages) {

        String packageName = clazz.getPackageName();

        if (isExcluded(packageName)) {
            return false;
        }

        if (basePackages.isEmpty()) {
            return true;
        }

        return basePackages.stream().anyMatch(base -> packageName.equals(base) || packageName.startsWith(base + "."));
    }

    private boolean isExcluded(String packageName) {

        for (String prefix : ALWAYS_EXCLUDED_PREFIXES) {
            if (packageName.startsWith(prefix)) {
                return true;
            }
        }

        for (String prefix : properties.getExcludePackages()) {
            if (prefix != null && !prefix.isBlank() && packageName.startsWith(prefix)) {
                return true;
            }
        }

        return false;
    }

    private List<String> resolveBasePackages() {

        if (properties.getBasePackage() != null && !properties.getBasePackage().isBlank()) {
            return List.of(properties.getBasePackage());
        }

        try {
            List<String> packages = AutoConfigurationPackages.get(context.getAutowireCapableBeanFactory());
            return packages == null ? List.of() : packages;
        } catch (IllegalStateException ex) {
            return List.of();
        }
    }

    private Class<?> getRealClass(Object bean) {
        return org.springframework.aop.support.AopUtils.getTargetClass(bean);
    }

    private List<Class<?>> extractConstructorDependencies(Class<?> clazz) {

        for (Constructor<?> ctor : clazz.getDeclaredConstructors()) {
            if (ctor.getParameterCount() > 0) {
                return List.of(ctor.getParameterTypes());
            }
        }
        return List.of();
    }
}
