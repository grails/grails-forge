package org.grails.forge.feature.test

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class JUnitSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test dependencies"() {

        when:
        Map<String, String> output = generate(ApplicationType.WEB, new Options(TestFramework.JUNIT))
        final String buildGradle = output["build.gradle"]

        then:
        buildGradle.contains("testImplementation(\"org.junit.jupiter:junit-jupiter")
    }
}
