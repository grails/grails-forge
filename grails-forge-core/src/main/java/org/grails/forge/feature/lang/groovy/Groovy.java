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
package org.grails.forge.feature.lang.groovy;

import jakarta.inject.Singleton;
import org.grails.forge.application.ApplicationType;
import org.grails.forge.application.generator.GeneratorContext;
import org.grails.forge.build.dependencies.Dependency;
import org.grails.forge.feature.ApplicationFeature;
import org.grails.forge.feature.Feature;
import org.grails.forge.feature.FeatureContext;
import org.grails.forge.feature.lang.LanguageFeature;
import org.grails.forge.feature.test.Spock;
import org.grails.forge.options.Language;
import org.grails.forge.options.Options;

import java.util.List;
import java.util.Set;

@Singleton
public class Groovy implements LanguageFeature {

    private final List<GroovyApplicationFeature> applicationFeatures;

    public Groovy(List<GroovyApplicationFeature> applicationFeatures, Spock spock) {
        this.applicationFeatures = applicationFeatures;
    }

    @Override
    public String getName() {
        return "groovy";
    }

    @Override
    public void processSelectedFeatures(FeatureContext featureContext) {
        if (!featureContext.isPresent(ApplicationFeature.class)) {
            applicationFeatures.stream()
                    .filter(f -> f.supports(featureContext.getApplicationType()))
                    .findFirst()
                    .ifPresent(featureContext::addFeature);
        }
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.addDependency(Dependency.builder()
                .groupId("io.micronaut.groovy")
                .artifactId("micronaut-runtime-groovy")
                .compile());
    }

    @Override
    public boolean isGroovy() {
        return true;
    }

    @Override
    public boolean shouldApply(ApplicationType applicationType, Options options, Set<Feature> selectedFeatures) {
        return options.getLanguage() == Language.GROOVY;
    }
}
