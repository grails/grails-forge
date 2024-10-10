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
package org.grails.forge.feature.grailsWrapper;

import jakarta.inject.Singleton;
import org.grails.forge.application.ApplicationType;
import org.grails.forge.application.generator.GeneratorContext;
import org.grails.forge.template.BinaryTemplate;

@Singleton
public class GrailsWrapper implements GrailsWrapperFeature {

    @Override
    public String getName() {
        return "grails-wrapper";
    }

    @Override
    public String getTitle() {
        return "Grails Wrapper for Grails";
    }

    @Override
    public String getDescription() {
        return "Gives you the ability to run grails shell from terminal.";
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        generatorContext.addTemplate("grailsWrapperJar", new BinaryTemplate("grails-wrapper.jar", classLoader.getResource("grailswrapper/grails-wrapper.jar"), false));
        generatorContext.addTemplate("grailsWrapper", new BinaryTemplate("grailsw", classLoader.getResource("grailswrapper/grailsw"), true));
        generatorContext.addTemplate("grailsWrapperBat", new BinaryTemplate("grailsw.bat", classLoader.getResource("grailswrapper/grailsw.bat"), false));
    }

    @Override
    public boolean supports(ApplicationType applicationType) {
        return true;
    }
}
