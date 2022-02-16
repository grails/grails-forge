package org.grails.forge.feature.lang

import org.grails.forge.BeanContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.BuildTool
import org.grails.forge.options.Language
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class GroovyApplicationSpec extends BeanContextSpec implements CommandOutputFixture {
    void 'Application file is generated for a default application type with gradle and referenced in build.gradle mainClassName for language: groovy'() {
        when:
        def output = generate(
                ApplicationType.DEFAULT,
                new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE),
                []
        )

        then:
        output.containsKey("src/main/groovy/example/grails/Application.${Language.GROOVY.extension}".toString())

        when:
        def buildGradle = output['build.gradle']

        then:
        buildGradle.contains('mainClass.set("example.grails.Application")')
    }
}
