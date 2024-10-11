package org.grails.forge.feature.grails

import org.grails.forge.BeanContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class GrailsBaseSpec extends BeanContextSpec implements CommandOutputFixture {

    void "test grails base dependencies"() {

        when:
        def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.DEFAULT_OPTION))
        def buildGradle = output['build.gradle']

        then:
        buildGradle.contains("implementation(\"org.grails:grails-core\")")
        buildGradle.contains("implementation(\"org.grails:grails-web-boot\")")
        buildGradle.contains("implementation(\"org.grails:grails-logging\")")
    }

    void "test src/main directories are present"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.DEFAULT_OPTION))

        expect:
        output.containsKey("src/main/groovy/.gitkeep")
        output.containsKey("src/test/groovy/.gitkeep")
        output.containsKey("src/integration-test/groovy/.gitkeep")
    }
}