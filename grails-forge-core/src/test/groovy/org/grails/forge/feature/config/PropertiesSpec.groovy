package org.grails.forge.feature.config

import org.grails.forge.BeanContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.feature.FeaturePhase
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.Options
import spock.lang.Shared
import spock.lang.Subject
import spock.lang.Unroll

class PropertiesSpec extends BeanContextSpec implements CommandOutputFixture {

    @Shared
    @Subject
    Properties properties = beanContext.getBean(Properties)

    void "order is highest"() {
        expect:
        properties.order == FeaturePhase.HIGHEST.getOrder()
    }

    @Unroll
    void "properties supports #description application type"(ApplicationType applicationType, String description) {
        expect:
        properties.supports(applicationType)

        where:
        applicationType << ApplicationType.values()
        description = applicationType.name
    }

    void "test configuration files generated for properties feature"() {
        when:
        GeneratorContext generatorContext = buildGeneratorContext(['properties'], { context ->
            context.getBootstrapConfiguration().put("abc", 123)
            context.getConfiguration("test", ApplicationConfiguration.testConfig()).put("abc", 456)
            context.getConfiguration("prod", new ApplicationConfiguration("prod")).put("abc", 789)
        }, new Options())
        def output = generate(ApplicationType.WEB, generatorContext)

        then:
        output["grails-app/conf/application.properties"].readLines().find {it == /info.app.name=@info.app.name@/ }
        output["grails-app/conf/bootstrap.properties"].readLines().find { it == 'abc=123' }
        output["src/test/resources/application-test.properties"].readLines().find { it == 'abc=456' }
        output["grails-app/conf/application-prod.properties"].readLines().find { it == 'abc=789' }
    }
}
