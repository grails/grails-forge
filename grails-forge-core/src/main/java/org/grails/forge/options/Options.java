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
package org.grails.forge.options;

import io.micronaut.core.convert.ArgumentConversionContext;
import io.micronaut.core.convert.value.ConvertibleValues;
import io.micronaut.core.convert.value.ConvertibleValuesMap;
import org.grails.forge.application.OperatingSystem;
import org.grails.forge.util.VersionInfo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Options implements ConvertibleValues<Object> {

    private final OperatingSystem operatingSystem;

    private final Language language;
    private final TestFramework testFramework;
    private final BuildTool buildTool;
    private final JdkVersion javaVersion;
    private final ConvertibleValuesMap<Object> additionalOptions;

    public Options(Language language, TestFramework testFramework, BuildTool buildTool, JdkVersion javaVersion, OperatingSystem operatingSystem) {
        this(language, testFramework, buildTool, javaVersion, operatingSystem, Collections.emptyMap());
    }

    public Options(Language language, TestFramework testFramework, BuildTool buildTool, JdkVersion javaVersion) {
        this(language, testFramework, buildTool, javaVersion, OperatingSystem.DEFAULT, Collections.emptyMap());
    }

    public Options(Language language, TestFramework testFramework, BuildTool buildTool) {
        this(language, testFramework, buildTool, VersionInfo.getJavaVersion(), OperatingSystem.DEFAULT, Collections.emptyMap());
    }

    public Options(Language language, TestFramework testFramework) {
        this(language, testFramework, language.getDefaults().getBuild(), VersionInfo.getJavaVersion(), OperatingSystem.DEFAULT, Collections.emptyMap());
    }

    public Options(Language language, BuildTool buildTool) {
        this(language, language.getDefaults().getTest(), buildTool, VersionInfo.getJavaVersion(), OperatingSystem.DEFAULT, Collections.emptyMap());
    }

    public Options(Language language) {
        this(language, language.getDefaults().getTest(), language.getDefaults().getBuild(), VersionInfo.getJavaVersion(), OperatingSystem.DEFAULT, Collections.emptyMap());
    }

    public Options() {
        this(Language.DEFAULT_OPTION, TestFramework.DEFAULT_OPTION, BuildTool.GRADLE, VersionInfo.getJavaVersion(), OperatingSystem.DEFAULT, Collections.emptyMap());
    }

    public Options(Language language, TestFramework testFramework, BuildTool buildTool, OperatingSystem operatingSystem, Map<String, Object> additionalOptions) {
        this(language, testFramework, buildTool, VersionInfo.getJavaVersion(), operatingSystem, additionalOptions);
    }

    public Options(Language language, TestFramework testFramework, BuildTool buildTool, JdkVersion javaVersion, OperatingSystem operatingSystem, Map<String, Object> additionalOptions) {
        this.javaVersion = javaVersion;
        this.language = language;
        this.testFramework = testFramework;
        this.buildTool = buildTool;
        this.operatingSystem = operatingSystem;
        this.additionalOptions = new ConvertibleValuesMap<>(additionalOptions);
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public Language getLanguage() {
        return language;
    }

    public TestFramework getTestFramework() {
        return testFramework;
    }

    public BuildTool getBuildTool() {
        return buildTool;
    }

    @Override
    public Set<String> names() {
        return additionalOptions.names();
    }

    @Override
    public Collection<Object> values() {
        return additionalOptions.values();
    }

    @Override
    public <T> Optional<T> get(CharSequence name, ArgumentConversionContext<T> conversionContext) {
        return additionalOptions.get(name, conversionContext);
    }

    public JdkVersion getJavaVersion() {
        return javaVersion;
    }

    public Options withOperatingSystem(OperatingSystem operatingSystem) {
        return new Options(language, testFramework, buildTool, javaVersion, operatingSystem, additionalOptions.asMap());
    }

    public Options withLanguage(Language language) {
        return new Options(language, testFramework, buildTool, javaVersion, operatingSystem, additionalOptions.asMap());
    }

    public Options withTestFramework(TestFramework testFramework) {
        return new Options(language, testFramework, buildTool, javaVersion, operatingSystem, additionalOptions.asMap());
    }

    public Options withBuildTool(BuildTool buildTool) {
        return new Options(language, testFramework, buildTool, javaVersion, operatingSystem, additionalOptions.asMap());
    }

    public Options withJavaVersion(JdkVersion javaVersion) {
        return new Options(language, testFramework, buildTool, javaVersion, operatingSystem, additionalOptions.asMap());
    }
}
