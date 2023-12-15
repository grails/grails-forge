package org.grails.forge.build.gradle

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class GradleSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test build properties"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        final String gradleProps = output["gradle.properties"]

        expect:
        gradleProps.contains("org.gradle.caching=true")
        gradleProps.contains("org.gradle.daemon=true")
        gradleProps.contains("org.gradle.parallel=true")
        gradleProps.contains("org.gradle.jvmargs=-Dfile.encoding=UTF-8 -Xmx1024M")
    }

    void "test build gradle"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        final String buildGradle = output["build.gradle"]

        expect:
        buildGradle.contains("eclipse")
        buildGradle.contains("idea")
        buildGradle.contains("war")
    }

    void "test settings.gradle"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        final String settingsGradle = output["settings.gradle"]

        expect:
        settingsGradle.contains("pluginManagement")
        settingsGradle.contains("repositories")
        settingsGradle.contains("mavenLocal()")
        settingsGradle.contains("maven { url \"https://repo.grails.org/grails/core/\" }")
        settingsGradle.contains("gradlePluginPortal()")
        settingsGradle.contains("id \"org.grails.grails-web\" version \"6.1.1\"")
        settingsGradle.contains("id \"org.grails.grails-gsp\" version \"6.1.1\"")
        settingsGradle.contains("id \"com.bertramlabs.asset-pipeline\" version \"4.3.0\"")
    }

    void "test settings.gradle for REST-API"() {
        given:
        final def output = generate(ApplicationType.REST_API, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        final String settingsGradle = output["settings.gradle"]

        expect:
        settingsGradle.contains("pluginManagement")
        settingsGradle.contains("repositories")
        settingsGradle.contains("mavenLocal()")
        settingsGradle.contains("maven { url \"https://repo.grails.org/grails/core/\" }")
        settingsGradle.contains("gradlePluginPortal()")
        settingsGradle.contains("id \"org.grails.grails-web\" version \"6.1.1\"")
        settingsGradle.contains("id \"org.grails.plugins.views-json\" version \"3.1.1\"")
        !settingsGradle.contains("id \"org.grails.grails-gsp\" version \"6.1.1\"")
        !settingsGradle.contains("id \"com.bertramlabs.asset-pipeline\" version \"4.3.0\"")
    }

    void "test settings.gradle for REST-API for markup-views"() {
        given:
        final def output = generate(ApplicationType.REST_API, new Options(TestFramework.SPOCK, JdkVersion.JDK_11), ["views-markup"])
        final String settingsGradle = output["settings.gradle"]

        expect:
        settingsGradle.contains("pluginManagement")
        settingsGradle.contains("repositories")
        settingsGradle.contains("mavenLocal()")
        settingsGradle.contains("maven { url \"https://repo.grails.org/grails/core/\" }")
        settingsGradle.contains("gradlePluginPortal()")
        settingsGradle.contains("id \"org.grails.grails-web\" version \"6.1.1\"")
        settingsGradle.contains("id \"org.grails.plugins.views-markup\" version \"3.1.1\"")
        !settingsGradle.contains("id \"org.grails.plugins.views-json\" version \"3.1.1\"")
        !settingsGradle.contains("id \"org.grails.grails-gsp\" version \"6.1.1\"")
        !settingsGradle.contains("id \"com.bertramlabs.asset-pipeline\" version \"4.3.0\"")
    }
}
