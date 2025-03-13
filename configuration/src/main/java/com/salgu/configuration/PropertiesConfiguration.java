package com.salgu.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PropertiesConfiguration implements EnvironmentPostProcessor {

    YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment,
                                       SpringApplication application) {
        final var profiles = Arrays.asList(environment.getActiveProfiles());
        final var propertyPaths = List.of(
                "properties/datasource.yml"
        );
        this.loadPropertiesFor(propertyPaths, profiles, environment);
    }

    private void loadPropertiesFor(List<String> propertyPaths,
                                   List<String> profiles,
                                   ConfigurableEnvironment environment) {
        propertyPaths.forEach(path -> {
            final var pathResource = this.generateResourceFrom(path);
            this.toPropertySourceFrom(pathResource, profiles, environment);
        });
    }

    private ClassPathResource generateResourceFrom(String resourcePath) {
        return new ClassPathResource(resourcePath);
    }

    private void toPropertySourceFrom(ClassPathResource classPathResource,
                                      List<String> profiles,
                                      ConfigurableEnvironment environment) {
        final var propertySources = this.loadFrom(classPathResource, profiles);
        propertySources.forEach(source -> environment.getPropertySources().addLast(source));
    }

    private List<PropertySource<?>> loadFrom(Resource resource, List<String> profiles) {
        try {
            if (this.invalid(resource)) {
                return new ArrayList<>();
            }

            final var propertySources = this.sourceLoader.load(resource.getDescription(), resource);
            return convertToListFor(profiles, propertySources);
        } catch (IOException ioException) {
            return new ArrayList<>();
        }
    }

    private boolean invalid(Resource resource) {
        return !resource.exists();
    }

    private List<PropertySource<?>> convertToListFor(List<String> profiles,
                                                     List<PropertySource<?>> propertySources) {
        final var propertyName = "spring.profiles.active";
        return propertySources.stream()
                .filter(source -> !source.containsProperty(propertyName) ||
                        profiles.contains(Objects.requireNonNull(source.getProperty(propertyName)).toString()) ||
                        Arrays.asList(Objects.requireNonNull(source.getProperty(propertyName)).toString().split(","))
                                .contains(profiles.getFirst()))
                .toList();
    }
}
