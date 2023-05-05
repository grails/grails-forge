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
package org.grails.forge.api;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.Get;
import io.swagger.v3.oas.annotations.Parameter;
import org.grails.forge.application.ApplicationType;
import org.grails.forge.options.BuildTool;
import org.grails.forge.options.GormImpl;
import org.grails.forge.options.JdkVersion;

/**
 * Operations on application types.
 *
 * @author graemerocher
 * @since 6.0.0
 */
public interface ApplicationTypeOperations {

    /**
     * List the application types.
     * @param serverURL The server URL
     * @return The types
     */
    @Get("/application-types")
    ApplicationTypeList list(@Parameter(hidden = true) RequestInfo serverURL);

    /**
     * Get a specific application type.
     * @param type The type
     * @param serverURL The server URL
     * @return The type
     */
    @Get("/application-types/{type}")
    ApplicationTypeDTO getType(ApplicationType type, @Parameter(hidden = true) RequestInfo serverURL);

    /**
     * List the type features.
     * @param type The features
     * @param serverURL The server URL
     * @param build The build tool
     * @param test The test framework
     * @param gorm The GORM
     * @param javaVersion The java version
     * @return The features
     */
    @Get("/application-types/{type}/features{?gorm,build,test,javaVersion}")
    FeatureList features(ApplicationType type,
                         @Parameter(hidden = true) RequestInfo serverURL,
                         @Nullable BuildTool build,
                         @Nullable TestFramework test,
                         @Nullable GormImpl gorm,
                         @Nullable JdkVersion javaVersion);

    @Get("/application-types/{type}/features/default{?gorm,build,test,javaVersion}")
    FeatureList defaultFeatures(ApplicationType type,
                                @Parameter(hidden = true) RequestInfo serverURL,
                                @Nullable BuildTool build,
                                @Nullable TestFramework test,
                                @Nullable GormImpl gorm,
                                @Nullable JdkVersion javaVersion);
}
