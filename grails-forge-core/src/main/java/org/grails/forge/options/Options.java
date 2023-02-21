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

import java.util.*;

public class Options implements ConvertibleValues<Object> {

    private final OperatingSystem operatingSystem;

    private final Language language;
    private final TestFramework testFramework;
    private final BuildTool buildTool;

    private final GormImpl gormImpl;
    private final JdkVersion javaVersion;
    private final ConvertibleValuesMap<Object> additionalOptions;

    public Options(Language language,
                   TestFramework testFramework,
                   BuildTool buildTool,
                   GormImpl gormImpl,
                   JdkVersion javaVersion,
                   OperatingSystem operatingSystem,
                   Map<String, Object> additionalOptions) {

        this.language = language;
        this.testFramework = testFramework;
        this.buildTool = buildTool;
        this.gormImpl = gormImpl;
        this.javaVersion = javaVersion;
        this.operatingSystem = operatingSystem;
        this.additionalOptions = new ConvertibleValuesMap<>(additionalOptions);
    }

    public Options(Language language,
                   TestFramework testFramework,
                   BuildTool buildTool,
                   GormImpl gormImpl,
                   JdkVersion javaVersion,
                   OperatingSystem operatingSystem) {

        this(language, testFramework, buildTool, gormImpl, javaVersion, operatingSystem, Collections.emptyMap());
    }

    public Options(Language language,
                   TestFramework testFramework,
                   BuildTool buildTool,
                   JdkVersion javaVersion,
                   OperatingSystem operatingSystem) {

        this(language, testFramework, buildTool, GormImpl.DEFAULT_OPTION, javaVersion, operatingSystem, Collections.emptyMap());
    }

    public Options(Language language,
                   TestFramework testFramework,
                   BuildTool buildTool,
                   JdkVersion javaVersion) {

        this(language, testFramework, buildTool, GormImpl.DEFAULT_OPTION, javaVersion, OperatingSystem.DEFAULT, Collections.emptyMap());
    }

    public Options(Language language,
                   TestFramework testFramework,
                   BuildTool buildTool) {

        this(language, testFramework, buildTool, GormImpl.DEFAULT_OPTION, VersionInfo.getJavaVersion(), OperatingSystem.DEFAULT, Collections.emptyMap());
    }

    public Options(Language language, TestFramework testFramework) {
        this(language, testFramework, language.getDefaults().getBuild(), GormImpl.DEFAULT_OPTION, VersionInfo.getJavaVersion(), OperatingSystem.DEFAULT, Collections.emptyMap());
    }

    public Options(Language language) {
        this(language, language.getDefaults().getTest(), language.getDefaults().getBuild(), GormImpl.DEFAULT_OPTION, VersionInfo.getJavaVersion(), OperatingSystem.DEFAULT, Collections.emptyMap());
    }

    public Options() {
        this(Language.DEFAULT_OPTION, TestFramework.DEFAULT_OPTION, BuildTool.GRADLE, GormImpl.DEFAULT_OPTION, VersionInfo.getJavaVersion(), OperatingSystem.DEFAULT, Collections.emptyMap());
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

    public GormImpl getGormImpl() {
        return gormImpl;
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
        return new Options(language, testFramework, buildTool, gormImpl, javaVersion, operatingSystem, additionalOptions.asMap());
    }

    public Options withLanguage(Language language) {
        return new Options(language, testFramework, buildTool, gormImpl, javaVersion, operatingSystem, additionalOptions.asMap());
    }

    public Options withTestFramework(TestFramework testFramework) {
        return new Options(language, testFramework, buildTool, gormImpl, javaVersion, operatingSystem, additionalOptions.asMap());
    }

    public Options withBuildTool(BuildTool buildTool) {
        return new Options(language, testFramework, buildTool, gormImpl, javaVersion, operatingSystem, additionalOptions.asMap());
    }

    public Options withGormImpl(GormImpl gormImpl) {
        return new Options(language, testFramework, buildTool, gormImpl, javaVersion, operatingSystem, additionalOptions.asMap());
    }

    public Options withJavaVersion(JdkVersion javaVersion) {
        return new Options(language, testFramework, buildTool, gormImpl, javaVersion, operatingSystem, additionalOptions.asMap());
    }
}
