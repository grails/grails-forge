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
package org.grails.forge.application;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.naming.Named;

import java.util.Locale;

public enum ApplicationType implements Named {

    WEB_APP("Grails Web Application", "A Grails® framework Web Application"),
    DEFAULT("Grails Web Application", "A Grails® framework Web Application");

    public static final ApplicationType DEFAULT_OPTION = DEFAULT;

    private final String title;
    private final String description;

    ApplicationType(String title,
                    String description) {
        this.title = title;
        this.description = description;
    }

    /**
     * @return The title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    @NonNull
    @Override
    public String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }
}
