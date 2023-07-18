package org.grails.forge.feature.test

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.application.OperatingSystem
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework
import spock.lang.Unroll

class GebSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test dependencies"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        final def buildGradle = output["build.gradle"]

        expect:
        buildGradle.contains("testImplementation(\"org.grails.plugins:geb\")")
        buildGradle.contains("testImplementation(\"org.seleniumhq.selenium:selenium-api:4.10.0\")")
        buildGradle.contains("testImplementation(\"org.seleniumhq.selenium:selenium-support:4.10.0\")")
        buildGradle.contains("testImplementation(\"org.seleniumhq.selenium:selenium-remote-driver:4.10.0\")")
        buildGradle.contains("testRuntimeOnly(\"org.seleniumhq.selenium:selenium-chrome-driver:4.10.0\")")
        buildGradle.contains("testRuntimeOnly(\"org.seleniumhq.selenium:selenium-firefox-driver:4.10.0\")")
    }

    void "test GebConfig.groovy file is present"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))

        expect:
        output.containsKey('src/integration-test/resources/GebConfig.groovy')
    }

    void "test build.gradle contains logic to force selenium version"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        final def buildGradle = output["build.gradle"]

        expect:
        buildGradle.contains("if (details.requested.group == 'org.seleniumhq.selenium') {\n" +
                "                details.useVersion('4.10.0')\n" +
                "            }")
    }

    @Unroll
    void "test feature geb is not supported for #applicationType application"(ApplicationType applicationType) {
        when:
        generate(applicationType, new Options(TestFramework.SPOCK, JdkVersion.JDK_11), ["geb"])

        then:
        def e = thrown(IllegalArgumentException)
        e.message == 'The requested feature does not exist: geb'

        where:
        applicationType << [ApplicationType.PLUGIN, ApplicationType.REST_API]
    }

    void "test webdriver binaries gradle configurations"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        final def buildGradle = output["build.gradle"]

        expect:
        buildGradle.contains("id \"com.github.erdi.webdriver-binaries\" version \"3.0\"")
        buildGradle.contains("webdriverBinaries")
        buildGradle.contains("chromedriver '110.0.5481.77'")
        buildGradle.contains("geckodriver '0.32.2'")
        buildGradle.contains("edgedriver '110.0.1587.57'")
    }

    void "test webdriver binaries gradle configurations for windows OS"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11, OperatingSystem.WINDOWS))
        final def buildGradle = output["build.gradle"]

        expect:
        buildGradle.contains("id \"com.github.erdi.webdriver-binaries\" version \"3.0\"")
        buildGradle.contains("webdriverBinaries")
        buildGradle.contains("chromedriver '110.0.5481.77'")
        buildGradle.contains("geckodriver '0.32.2'")
        buildGradle.contains("iedriverserver '3.141.59'")
        buildGradle.contains("edgedriver '110.0.1587.57'")
    }
}
