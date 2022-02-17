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
        def output = generate(ApplicationType.DEFAULT, generatorContext)

        then:
        output["src/main/resources/application.properties"].readLines()[2] == 'info.app.name=foo'
        output["src/main/resources/bootstrap.properties"].readLines()[1] == 'abc=123'
        output["src/test/resources/application-test.properties"].readLines()[1] == 'abc=456'
        output["src/main/resources/application-prod.properties"].readLines()[1] == 'abc=789'
    }
}
