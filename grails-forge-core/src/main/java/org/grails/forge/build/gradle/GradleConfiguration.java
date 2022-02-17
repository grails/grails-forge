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
package org.grails.forge.build.gradle;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.order.Ordered;
import org.grails.forge.build.dependencies.Phase;
import org.grails.forge.build.dependencies.Scope;
import org.grails.forge.options.Language;
import org.grails.forge.options.TestFramework;

import java.util.Optional;

public enum GradleConfiguration implements Ordered {
    BUILD("classpath", 0),
    ANNOTATION_PROCESSOR("annotationProcessor", 0),
    KAPT("kapt", 1),
    API("api", 2),
    IMPLEMENTATION("implementation", 3),
    COMPILE_ONLY("compileOnly", 4),
    CONSOLE("console", 5),
    RUNTIME_ONLY("runtimeOnly", 6),
    TEST_ANNOTATION_PROCESSOR("testAnnotationProcessor", 8),
    TEST_KAPT("kaptTest", 9),
    TEST_IMPLEMENTATION("testImplementation", 10),
    TEST_COMPILE_ONLY("testCompileOnly", 11),
    TEST_RUNTIME_ONLY("testRuntimeOnly", 12),
    OPENREWRITE("rewrite", 13);

    private final String configurationName;
    private final int order;

    GradleConfiguration(String configurationName, int order) {
        this.configurationName = configurationName;
        this.order = order;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    @Override
    public String toString() {
        return this.configurationName;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @NonNull
    public static Optional<GradleConfiguration> of(@NonNull Scope scope,
                                                   @NonNull Language language,
                                                   @NonNull TestFramework testFramework) {
        switch (scope.getSource()) {
            case BUILD_SRC:
                if (scope.getPhases().contains(Phase.BUILD)) {
                    return Optional.of(GradleConfiguration.BUILD);
                }
                break;
            case MAIN:
                if (scope.getPhases().contains(Phase.ANNOTATION_PROCESSING)) {
                    return Optional.of(GradleConfiguration.COMPILE_ONLY);
                }
                if (scope.getPhases().contains(Phase.CONSOLE)) {
                    return Optional.of(GradleConfiguration.CONSOLE);
                }
                if (scope.getPhases().contains(Phase.RUNTIME)) {
                    if (scope.getPhases().contains(Phase.COMPILATION)) {
                        return Optional.of(GradleConfiguration.IMPLEMENTATION);
                    }
                    return Optional.of(GradleConfiguration.RUNTIME_ONLY);
                }
                if (scope.getPhases().contains(Phase.COMPILATION)) {
                    return Optional.of(GradleConfiguration.COMPILE_ONLY);
                }
                if (scope.getPhases().contains(Phase.OPENREWRITE)) {
                    return Optional.of(GradleConfiguration.OPENREWRITE);
                }
                break;

            case TEST:
                if (scope.getPhases().contains(Phase.ANNOTATION_PROCESSING)) {
                    return Optional.of(GradleConfiguration.TEST_COMPILE_ONLY);
                }
                if (scope.getPhases().contains(Phase.RUNTIME)) {
                    if (scope.getPhases().contains(Phase.COMPILATION)) {
                        return Optional.of(GradleConfiguration.TEST_IMPLEMENTATION);
                    }
                    return Optional.of(GradleConfiguration.TEST_RUNTIME_ONLY);
                }
                if (scope.getPhases().contains(Phase.COMPILATION)) {
                    return Optional.of(GradleConfiguration.TEST_COMPILE_ONLY);
                }
                break;

            default:
                return Optional.empty();
        }
        return Optional.empty();
    }
}
