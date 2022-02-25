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
package org.grails.forge.feature.view;

import jakarta.inject.Singleton;
import org.grails.forge.application.ApplicationType;
import org.grails.forge.application.generator.GeneratorContext;
import org.grails.forge.build.dependencies.Dependency;
import org.grails.forge.build.gradle.GradlePlugin;
import org.grails.forge.feature.Category;
import org.grails.forge.feature.DefaultFeature;
import org.grails.forge.feature.Feature;
import org.grails.forge.feature.FeatureContext;
import org.grails.forge.feature.view.templates.error;
import org.grails.forge.feature.view.templates.index;
import org.grails.forge.feature.view.templates.mainLayout;
import org.grails.forge.feature.view.templates.notFound;
import org.grails.forge.feature.web.GrailsWeb;
import org.grails.forge.options.Options;
import org.grails.forge.template.RockerTemplate;

import java.util.Map;
import java.util.Set;

@Singleton
public class GrailsGsp implements DefaultFeature {

    private final GrailsWeb grailsWeb;

    public GrailsGsp(GrailsWeb grailsWeb) {
        this.grailsWeb = grailsWeb;
    }

    @Override
    public boolean shouldApply(ApplicationType applicationType, Options options, Set<Feature> selectedFeatures) {
        return true;
    }

    @Override
    public String getName() {
        return "grails-gsp";
    }

    @Override
    public boolean supports(ApplicationType applicationType) {
        return applicationType == ApplicationType.DEFAULT;
    }

    @Override
    public String getCategory() {
        return Category.VIEW;
    }

    @Override
    public void processSelectedFeatures(FeatureContext featureContext) {
        if (!featureContext.isPresent(GrailsWeb.class) && grailsWeb != null) {
            featureContext.addFeature(grailsWeb);
        }
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        final Map<String, Object> config = generatorContext.getConfiguration();
        config.put("grails.views.gsp.encoding", "UTF-8");
        config.put("grails.views.gsp.htmlcodec", "xml");
        config.put("grails.views.gsp.codecs.expression", "html");
        config.put("grails.views.gsp.codecs.scriptlet", "html");
        config.put("grails.views.gsp.codecs.taglib", "none");
        config.put("grails.views.gsp.codecs.staticparts", "none");
        generatorContext.addDependency(Dependency.builder()
                .groupId("org.grails.plugins")
                .artifactId("gsp")
                .compile());
        generatorContext.addBuildPlugin(GradlePlugin.builder().id("org.grails.grails-gsp").build());

        generatorContext.addTemplate("mainLayout", new RockerTemplate(getViewFolderPath() + "layouts/main.gsp", mainLayout.template()));
        generatorContext.addTemplate("index", new RockerTemplate(getViewFolderPath() + "index.gsp", index.template()));
        generatorContext.addTemplate("error", new RockerTemplate(getViewFolderPath() + "error.gsp", error.template()));
        generatorContext.addTemplate("notFound", new RockerTemplate(getViewFolderPath() + "notFound.gsp", notFound.template()));
    }

    protected String getViewFolderPath() {
        return "grails-app/views/";
    }
}
