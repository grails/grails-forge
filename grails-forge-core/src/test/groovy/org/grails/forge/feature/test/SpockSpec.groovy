package org.grails.forge.feature.test

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture

class SpockSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test dependencies"() {

        when:
        Map<String, String> output = generate(ApplicationType.WEB)
        final String buildGradle = output["build.gradle"]

        then:
        buildGradle.contains("testImplementation(\"org.spockframework:spock-core\")")
        buildGradle.contains("useJUnitPlatform()")
    }
}
