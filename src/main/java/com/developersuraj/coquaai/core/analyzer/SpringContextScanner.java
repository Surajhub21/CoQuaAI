package com.developersuraj.coquaai.core.analyzer;

import com.developersuraj.coquaai.Entity.ComponentInfo;
import com.developersuraj.coquaai.Entity.ComponentType;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class SpringContextScanner {

    private final ApplicationContext context;

    public SpringContextScanner(ApplicationContext context) {
        this.context = context;
    }

    public List<ComponentInfo> scan() {

        List<ComponentInfo> components = new ArrayList<>();

        // Controllers
        context.getBeansWithAnnotation(RestController.class)
                .values()
                .forEach(bean ->
                        components.add(
                                buildComponent(bean, ComponentType.CONTROLLER)));

        context.getBeansWithAnnotation(Controller.class)
                .values()
                .forEach(bean ->
                        components.add(buildComponent(bean, ComponentType.CONTROLLER)));

        // Services
        context.getBeansWithAnnotation(Service.class)
                .values()
                .forEach(bean ->
                        components.add(
                                buildComponent(bean, ComponentType.SERVICE)));

        // Repositories (Spring Data)
        context.getBeansOfType(
                        org.springframework.data.repository.Repository.class)
                .values()
                .forEach(bean ->
                        components.add(
                                new ComponentInfo(
                                        getRealClass(bean),
                                        ComponentType.REPOSITORY,
                                        List.of()
                                )));

        return components;
    }

    //Helper Class
    private ComponentInfo buildComponent(
            Object bean,
            ComponentType type) {

        Class<?> realClass = getRealClass(bean);

        List<Class<?>> dependencies = extractConstructorDependencies(realClass);

        return new ComponentInfo(
                realClass,
                type,
                dependencies
        );
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