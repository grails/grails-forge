/*
 * Copyright 2017-2024 original authors
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

import static org.grails.forge.feature.config.ConfigurationFeature.DEV_ENVIRONMENT_KEY;
import static org.grails.forge.feature.config.ConfigurationFeature.TEST_ENVIRONMENT_KEY;
import static org.grails.forge.feature.config.ConfigurationFeature.PROD_ENVIRONMENT_KEY;
import static org.grails.forge.feature.config.ConfigurationFeature.ENVIRONMENTS_KEY;
import static org.grails.forge.feature.config.ConfigurationFeature.PROPERTIES_KEY;
import static org.grails.forge.feature.database.HibernateGorm.PREFIX;

/**
 * A feature that configures a datasource with a driver
 */
public interface DatabaseDriverConfigurationFeature extends Feature {

    String getUrlKey();

    String getDriverKey();

    String getUsernameKey();

    String getPasswordKey();

    String getDbCreateKey();

    default void applyDefaultConfig(DatabaseDriverFeature dbFeature, Map<String, Object> config) {
        Optional.ofNullable(dbFeature.getDriverClass()).ifPresent(driver -> config.put(getDriverKey(), driver));
        Optional.ofNullable(dbFeature.getDefaultUser()).ifPresent(user -> config.put(getUsernameKey(), user));
        Optional.ofNullable(dbFeature.getDefaultPassword()).ifPresent(pass -> config.put(getPasswordKey(), pass));

        config.put(ENVIRONMENTS_KEY + "." + DEV_ENVIRONMENT_KEY + "." + getDbCreateKey(), "create-drop");
        Optional.ofNullable(dbFeature.getJdbcDevUrl()).ifPresent(url -> config.put(ENVIRONMENTS_KEY + "." + DEV_ENVIRONMENT_KEY + "." + getUrlKey(), url));
        config.put(ENVIRONMENTS_KEY + "." + TEST_ENVIRONMENT_KEY + "." + getDbCreateKey(), "update");
        Optional.ofNullable(dbFeature.getJdbcTestUrl()).ifPresent(url -> config.put(ENVIRONMENTS_KEY + "." + TEST_ENVIRONMENT_KEY + "." + getUrlKey(), url));
        config.put(ENVIRONMENTS_KEY + "." + PROD_ENVIRONMENT_KEY + "." + getDbCreateKey(), "none");
        Optional.ofNullable(dbFeature.getJdbcProdUrl()).ifPresent(url -> config.put(ENVIRONMENTS_KEY + "." + PROD_ENVIRONMENT_KEY + "." + getUrlKey(), url));

        addProductionDataSourceProperties(config, "jmxEnabled", true);
        addProductionDataSourceProperties(config, "initialSize", 5);
        addProductionDataSourceProperties(config, "maxActive", 50);
        addProductionDataSourceProperties(config, "minIdle", 5);
        addProductionDataSourceProperties(config, "maxIdle", 25);
        addProductionDataSourceProperties(config, "maxWait", 10000);
        addProductionDataSourceProperties(config, "maxAge", 600000);
        addProductionDataSourceProperties(config, "timeBetweenEvictionRunsMillis", 5000);
        addProductionDataSourceProperties(config, "minEvictableIdleTimeMillis", 60000);
        addProductionDataSourceProperties(config, "validationQuery", "SELECT 1");
        addProductionDataSourceProperties(config, "validationQueryTimeout", 3);
        addProductionDataSourceProperties(config, "validationInterval", 15000);
        addProductionDataSourceProperties(config, "testOnBorrow", true);
        addProductionDataSourceProperties(config, "testWhileIdle", true);
        addProductionDataSourceProperties(config, "testOnReturn", false);
        addProductionDataSourceProperties(config, "jdbcInterceptors", "ConnectionState");
        addProductionDataSourceProperties(config, "defaultTransactionIsolation", 2);

        final Map<String, Object> additionalConfig = dbFeature.getAdditionalConfig();
        if (!additionalConfig.isEmpty()) {
            config.putAll(additionalConfig);
        }
    }

    default void addProductionDataSourceProperties(Map<String, Object> config, String key, Object value) {
        config.put(ENVIRONMENTS_KEY + "." + PROD_ENVIRONMENT_KEY + "." + PREFIX + PROPERTIES_KEY + "." + key, value);
    }
}
