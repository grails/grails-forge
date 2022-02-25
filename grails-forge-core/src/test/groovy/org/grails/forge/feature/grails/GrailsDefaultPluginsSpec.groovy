package org.grails.forge.feature.grails

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.BuildTool
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Language
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class GrailsDefaultPluginsSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test that default grails plugins are present"() {
        given:
        final Map<String, String> output = generate(ApplicationType.DEFAULT, new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11))
        final String buildGradle = output["build.gradle"]

        expect:
        buildGradle.contains("implementation(\"org.grails:grails-plugin-rest\")")
        buildGradle.contains("implementation(\"org.grails:grails-plugin-databinding\")")
        buildGradle.contains("implementation(\"org.grails:grails-plugin-i18n\")")
        buildGradle.contains("implementation(\"org.grails:grails-plugin-services\")")
        buildGradle.contains("implementation(\"org.grails:grails-plugin-interceptors\")")
    }

}
