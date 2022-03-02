/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.forge.feature.grails;

import jakarta.inject.Singleton;
import org.grails.forge.application.ApplicationType;
import org.grails.forge.application.generator.GeneratorContext;
import org.grails.forge.build.dependencies.Dependency;
import org.grails.forge.feature.Category;
import org.grails.forge.feature.DefaultFeature;
import org.grails.forge.feature.Feature;
import org.grails.forge.options.Options;
import org.grails.forge.template.URLTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Singleton
public class GrailsDefaultPlugins implements DefaultFeature {
    @Override
    public boolean shouldApply(ApplicationType applicationType, Options options, Set<Feature> selectedFeatures) {
        return true;
    }

    @Override
    public String getName() {
        return "grails-dependencies";
    }

    @Override
    public boolean isVisible() {
        return false;
    }

    @Override
    public boolean supports(ApplicationType applicationType) {
        return true;
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        Arrays.asList("rest", "databinding", "i18n", "services", "url-mappings", "interceptors").forEach((artifact) -> {
            generatorContext.addDependency(Dependency.builder()
                    .groupId("org.grails")
                    .artifactId("grails-plugin-" + artifact)
                    .compile());
        });
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            final Path path = Paths.get(Objects.requireNonNull(classLoader.getResource("i18n")).getPath());
            if (Files.exists(path)) {
                Files.walk(path)
                        .filter(Files::isRegularFile)
                        .forEach(file -> {
                            final String relativePath = "i18n/" + file.getFileName();
                            generatorContext.addTemplate(relativePath, new URLTemplate("grails-app/" + relativePath, classLoader.getResource(relativePath)));
                        });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getCategory() {
        return Category.SERVER;
    }
}
