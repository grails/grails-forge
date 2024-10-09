/*
 * Copyright 2017-2024 original authors
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
package org.grails.forge.feature.config;

import org.grails.forge.application.generator.GeneratorContext;
import org.grails.forge.feature.Category;
import org.grails.forge.feature.OneOfFeature;
import org.grails.forge.template.Template;

import java.util.function.Function;

public interface ConfigurationFeature extends OneOfFeature {

    final String ENVIRONMENTS_KEY = "environments";
    final String DEV_ENVIRONMENT_KEY = "development";
    final String TEST_ENVIRONMENT_KEY = "test";
    final String PROD_ENVIRONMENT_KEY = "production";
    final String PROPERTIES_KEY = "properties";

    @Override
    default Class<?> getFeatureClass() {
        return ConfigurationFeature.class;
    }

    @Override
    default String getCategory() {
        return Category.CONFIGURATION;
    }

    Function<Configuration, Template> createTemplate();

    @Override
    default void apply(GeneratorContext generatorContext) {
        Function<Configuration, Template> createTemplateFunc = createTemplate();
        generatorContext.getAllConfigurations()
                .stream()
                .filter(config -> !config.isEmpty())
                .forEach(config -> {
                    generatorContext.addTemplate(config.getTemplateKey(), createTemplateFunc.apply(config));
                });
    }
}
