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
package org.grails.forge.feature.assetPipeline;

import jakarta.inject.Singleton;
import org.grails.forge.application.ApplicationType;
import org.grails.forge.application.generator.GeneratorContext;
import org.grails.forge.build.dependencies.Dependency;
import org.grails.forge.build.gradle.GradlePlugin;
import org.grails.forge.feature.Category;
import org.grails.forge.feature.DefaultFeature;
import org.grails.forge.feature.Feature;
import org.grails.forge.feature.assetPipeline.templates.assetPipelineExtension;
import org.grails.forge.options.Options;
import org.grails.forge.template.RockerWritable;

import java.util.Set;

@Singleton
public class AssetPipeline implements DefaultFeature {

    @Override
    public String getName() {
        return "asset-pipeline-grails";
    }

    @Override
    public String getTitle() {
        return "Asset Pipeline Core";
    }

    @Override
    public String getDescription() {
        return "The Asset-Pipeline is a plugin used for managing and processing static assets in JVM applications primarily via Gradle (however not mandatory). Read more at https://github.com/bertramdev/asset-pipeline";
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.addBuildPlugin(GradlePlugin.builder()
                .id("com.bertramlabs.asset-pipeline")
                .lookupArtifactId("asset-pipeline-grails")
                .extension(new RockerWritable(assetPipelineExtension.template()))
                .build());

        generatorContext.addDependency(Dependency.builder()
                .groupId("com.bertramlabs.plugins")
                .lookupArtifactId("asset-pipeline-grails")
                .runtime());
    }

    @Override
    public boolean supports(ApplicationType applicationType) {
        return true;
    }

    @Override
    public String getCategory() {
        return Category.VIEW;
    }

    @Override
    public String getDocumentation() {
        return "https://www.asset-pipeline.com/manual/";
    }

    @Override
    public boolean shouldApply(ApplicationType applicationType, Options options, Set<Feature> selectedFeatures) {
        return true;
    }
}
