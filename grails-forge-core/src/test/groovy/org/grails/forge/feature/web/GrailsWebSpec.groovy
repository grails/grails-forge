package org.grails.forge.feature.web

import groovy.yaml.YamlSlurper
import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.BuildTool
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Language
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class GrailsWebSpec extends ApplicationContextSpec implements CommandOutputFixture{

    void "test grails-web feature"() {
        given:
        final def output = generate(ApplicationType.DEFAULT, new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11))
        final def buildGradle = output["build.gradle"]

        expect:
        buildGradle.contains("id \"org.grails.grails-web\"")
    }

    void "test grails-web configuration"() {
        when:
        final def output = generate(ApplicationType.DEFAULT, new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11))
        final String applicationYaml = output["grails-app/conf/application.yml"]
        def config = new YamlSlurper().parseText(applicationYaml)


        then:
        config.grails.views.default.codec == 'html'
    }
}
