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
package org.grails.forge.feature.spring;

import org.grails.forge.feature.Category;
import org.grails.forge.feature.DefaultFeature;
import org.grails.forge.feature.OneOfFeature;

abstract class SpringBootEmbeddedServlet implements OneOfFeature, DefaultFeature {

    @Override
    public Class<?> getFeatureClass() {
        return SpringBootEmbeddedServlet.class;
    }

    @Override
    public String getCategory() {
        return Category.SERVER;
    }
}
