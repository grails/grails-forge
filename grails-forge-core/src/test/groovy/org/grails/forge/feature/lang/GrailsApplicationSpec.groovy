package org.grails.forge.feature.lang

import org.grails.forge.BeanContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Language
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework
import spock.lang.Unroll

class GrailsApplicationSpec extends BeanContextSpec implements CommandOutputFixture {

    @Unroll
    void 'Application file is generated for a #applicationType application type with gradle and referenced in build.gradle mainClassName for language: groovy'() {
        when:
        def output = generate(applicationType,
                new Options(TestFramework.SPOCK),
                [])

        then:
        output.containsKey("grails-app/init/example/grails/Application.${Language.GROOVY.extension}".toString())
        def applicationGroovyFile = output.get("grails-app/init/example/grails/Application.${Language.GROOVY.extension}".toString())
        applicationGroovyFile.contains("@CompileStatic")
        !applicationGroovyFile.contains("@PluginSource")

        when:
        def buildGradle = output['build.gradle']

        then:
        buildGradle.contains('mainClass.set("example.grails.Application")')

        where:
        applicationType << [ApplicationType.WEB, ApplicationType.REST_API]
    }

    void 'Application file is generated with annotation @PluginSource for #applicationType application type'() {
        when:
        def output = generate(applicationType,
                new Options(TestFramework.SPOCK),
                [])

        then:
        output.containsKey("grails-app/init/example/grails/Application.${Language.GROOVY.extension}".toString())
        def applicationGroovyFile = output.get("grails-app/init/example/grails/Application.${Language.GROOVY.extension}".toString())
        applicationGroovyFile.contains("@PluginSource")

        where:
        applicationType << [ApplicationType.PLUGIN, ApplicationType.WEB_PLUGIN]
    }

    void "REST-API application should have ApplicationController"() {
        when:
        def output = generate(ApplicationType.REST_API)

        then:
        output.containsKey("grails-app/controllers/example/grails/ApplicationController.groovy")
    }

    void "PluginDescriptor is generated for #applicationType application type"() {
        when:
        def output = generate(applicationType)

        then:
        output.containsKey('src/main/groovy/example/grails/FooGrailsPlugin.groovy')
        def pluginGroovy = output.get("src/main/groovy/example/grails/FooGrailsPlugin.groovy")

        where:
        applicationType << [ApplicationType.PLUGIN, ApplicationType.WEB_PLUGIN]
    }

    void "PluginDescriptor is NOT generated for #applicationType application type"() {
        when:
        def output = generate(applicationType)

        then:
        !output.containsKey('src/main/groovy/example/grails/FooGrailsPlugin.groovy')

        where:
        applicationType << [ApplicationType.WEB, ApplicationType.REST_API]
    }
  
    void "ApplicationController is not present for #applicationType application type"() {      
        !output.containsKey("grails-app/controllers/example/grails/ApplicationController.groovy")

        where:
        applicationType << [ApplicationType.WEB, ApplicationType.PLUGIN, ApplicationType.WEB_PLUGIN]
    }

    void "test build plugins"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        def buildGradle = output['build.gradle']

        expect:
        buildGradle.contains("war")
    }
}
