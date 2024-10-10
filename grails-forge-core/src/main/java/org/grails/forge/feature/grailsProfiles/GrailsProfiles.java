/*
 * Copyright 2024 original authors
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
package org.grails.forge.feature.grailsProfiles;

import jakarta.inject.Singleton;
import org.grails.forge.application.ApplicationType;
import org.grails.forge.application.generator.GeneratorContext;
import org.grails.forge.build.dependencies.Dependency;
import org.grails.forge.build.dependencies.Scope;
import org.grails.forge.feature.Category;
import org.grails.forge.feature.DefaultFeature;
import org.grails.forge.feature.Feature;
import org.grails.forge.options.Options;

import java.util.Map;
import java.util.Set;

@Singleton
public class GrailsProfiles implements DefaultFeature {
    @Override
    public boolean shouldApply(ApplicationType applicationType, Options options, Set<Feature> selectedFeatures) {
        return true;
    }

    @Override
    public String getName() {
        return "grails-profiles";
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


        final Map<String, Object> config = generatorContext.getConfiguration();
        // Required by profile commands when package is not set
        config.put("grails.codegen.defaultPackage", generatorContext.getProject().getPackageName());

        ApplicationType applicationType = generatorContext.getApplicationType();

        generatorContext.addDependency(Dependency.builder()
                .groupId("org.grails.profiles")
                .artifactId(applicationType.getName().replace("_", "-"))
                .scope(Scope.PROFILE));
    }

    @Override
    public String getCategory() {
        return Category.CONFIGURATION;
    }
}
