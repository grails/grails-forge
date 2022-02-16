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

import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Singleton;
import org.grails.forge.application.ApplicationType;
import org.grails.forge.application.Project;
import org.grails.forge.application.generator.GeneratorContext;
import org.grails.forge.feature.test.template.groovyJunit;
import org.grails.forge.feature.test.template.spock;
import org.grails.forge.options.DefaultTestRockerModelProvider;
import org.grails.forge.options.TestFramework;
import org.grails.forge.options.TestRockerModelProvider;
import org.grails.forge.template.RockerTemplate;

@Singleton
public class GroovyApplication implements GroovyApplicationFeature {

    @Override
    @Nullable
    public String mainClassName(GeneratorContext generatorContext) {
        return generatorContext.getProject().getPackageName() + ".Application";
    }

    @Override
    public String getName() {
        return "groovy-application";
    }

    @Override
    public boolean supports(ApplicationType applicationType) {
        return true;
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        GroovyApplicationFeature.super.apply(generatorContext);

        if (shouldGenerateApplicationFile(generatorContext)) {
            generatorContext.addTemplate("application", new RockerTemplate(getPath(),
                    application.template(generatorContext.getProject(), generatorContext.getFeatures())));
            TestFramework testFramework = generatorContext.getTestFramework();
            String testSourcePath = generatorContext.getTestSourcePath("/{packagePath}/{className}");
            Project project = generatorContext.getProject();
            TestRockerModelProvider provider = new DefaultTestRockerModelProvider(spock.template(project),
                    groovyJunit.template(project));
            generatorContext.addTemplate("applicationTest",
                    new RockerTemplate(testSourcePath, provider.findModel(generatorContext.getLanguage(), testFramework))
            );
        }
    }

    protected boolean shouldGenerateApplicationFile(GeneratorContext generatorContext) {
        return generatorContext.getApplicationType() == ApplicationType.DEFAULT;
    }

    protected String getPath() {
        return "src/main/groovy/{packagePath}/Application.groovy";
    }
}
