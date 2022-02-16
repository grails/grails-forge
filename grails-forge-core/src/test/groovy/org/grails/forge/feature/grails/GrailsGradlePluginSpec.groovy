package org.grails.forge.feature.grails

import org.grails.forge.BeanContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.BuildTool
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Language
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class GrailsGradlePluginSpec extends BeanContextSpec implements CommandOutputFixture {

    void "test build gradle file and gradle properties"() {
        when:
        final def output = generate(ApplicationType.DEFAULT, new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11))
        final String buildGradle = output["build.gradle"]
        final String gradleProps = output["gradle.properties"]

        then:
        buildGradle.contains('classpath("org.grails.plugins:grails-gradle-plugin:${grailsGradlePluginVersion}")')
        gradleProps.contains("grailsGradlePluginVersion=5.1.2")
    }
}
