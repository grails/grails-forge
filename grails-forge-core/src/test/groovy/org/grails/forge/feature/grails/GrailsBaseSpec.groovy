package org.grails.forge.feature.grails

import org.grails.forge.BeanContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.BuildTool
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Language
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class GrailsBaseSpec extends BeanContextSpec implements CommandOutputFixture {

    void "test grails base dependencies"() {

        when:
        def output = generate(ApplicationType.DEFAULT, new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11))
        def buildGradle = output['build.gradle']

        then:
        buildGradle.contains("implementation(\"org.grails:grails-core\")")
        buildGradle.contains("implementation(\"org.grails:grails-web-boot\")")
        buildGradle.contains("implementation(\"org.grails:grails-logging\")")
    }
}