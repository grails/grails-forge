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

import org.grails.forge.feature.Feature;

import java.util.Map;
import java.util.Optional;

import static org.grails.forge.feature.config.ConfigurationFeature.DEV_ENV_KEY;
import static org.grails.forge.feature.config.ConfigurationFeature.ENV_KEY;

/**
 * A feature that configures a datasource with a driver
 */
public interface DatabaseDriverConfigurationFeature extends Feature {

    String getUrlKey();

    String getDriverKey();

    String getUsernameKey();

    String getPasswordKey();

    default void applyDefaultConfig(DatabaseDriverFeature dbFeature, Map<String, Object> config) {
        Optional.ofNullable(dbFeature.getJdbcUrl()).ifPresent(url -> config.put(ENV_KEY + "." + DEV_ENV_KEY + "." + getUrlKey(), url));
        Optional.ofNullable(dbFeature.getDriverClass()).ifPresent(driver -> config.put(getDriverKey(), driver));
        Optional.ofNullable(dbFeature.getDefaultUser()).ifPresent(user -> config.put(getUsernameKey(), user));
        Optional.ofNullable(dbFeature.getDefaultPassword()).ifPresent(pass -> config.put(getPasswordKey(), pass));
        final Map<String, Object> additionalConfig = dbFeature.getAdditionalConfig();
        if (!additionalConfig.isEmpty()) {
            config.putAll(additionalConfig);
        }
    }
}
