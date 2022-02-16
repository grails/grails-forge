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
package org.grails.forge.feature.validation;

import org.grails.forge.application.ApplicationType;
import org.grails.forge.feature.Feature;
import org.grails.forge.options.Options;

import java.util.Set;

public interface FeatureValidator {

    void validatePreProcessing(Options options, ApplicationType applicationType, Set<Feature> features);

    void validatePostProcessing(Options options, ApplicationType applicationType, Set<Feature> features);
}
