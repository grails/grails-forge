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
package org.grails.forge.feature.database;

import jakarta.inject.Singleton;
import org.grails.forge.application.generator.GeneratorContext;
import org.grails.forge.build.dependencies.Dependency;
import org.grails.forge.feature.OneOfFeature;

import java.util.Map;

@Singleton
public class Neo4jGorm extends GormOneOfFeature implements OneOfFeature {

    @Override
    public String getName() {
        return "gorm-neo4j";
    }

    @Override
    public String getTitle() {
        return "GORM for Neo4j";
    }

    @Override
    public String getDescription() {
        return "Configures GORM for Neo4j for Groovy applications";
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        Map<String, Object> config = generatorContext.getConfiguration();
        applyDefaultGormConfig(config);
        config.put("grails.neo4j.type", "embedded");
        config.put("grails.neo4j.location", "build/data/neo4j");
        generatorContext.addDependency(Dependency.builder()
                .groupId("org.grails.plugins")
                .artifactId("neo4j")
                .compile());
    }
}
