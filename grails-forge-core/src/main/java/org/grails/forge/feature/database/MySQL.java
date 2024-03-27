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

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;
import org.grails.forge.application.generator.GeneratorContext;
import org.grails.forge.build.dependencies.Dependency;

@Singleton
public class MySQL extends DatabaseDriverFeature {

    public MySQL(HibernateGorm hibernateGorm, TestContainers testContainers) {
        super(hibernateGorm, testContainers);
    }

    @Override
    @NonNull
    public String getName() {
        return "mysql";
    }

    @Override
    public String getTitle() {
        return "MySQL";
    }

    @Override
    public String getDescription() {
        return "Adds the MySQL driver and default config";
    }

    @Override
    public String getJdbcDevUrl() {
        return "jdbc:mysql://localhost:3306/devDb";
    }

    @Override
    public String getJdbcTestUrl() {
        return "jdbc:mysql://localhost:3306/testDb";
    }

    @Override
    public String getJdbcProdUrl() {
        return "jdbc:mysql://localhost:3306/db";
    }

    @Override
    public String getDriverClass() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public String getDefaultUser() {
        return "root";
    }

    @Override
    public String getDefaultPassword() {
        return "";
    }

    @Override
    public String getDataDialect() {
        return "MYSQL";
    }

    @Override
    public boolean embedded() {
        return false;
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        generatorContext.addDependency(Dependency.builder()
                .groupId("mysql")
                .artifactId("mysql-connector-java")
                .runtime());
    }
}
