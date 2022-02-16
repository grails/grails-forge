package org.grails.forge.feature.database

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.feature.Features
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.Language

class EmbeddedMongoSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test embedded mongo feature"() {
        when:
        Features features = getFeatures(['embedded-mongodb'])

        then:
        features.contains('embedded-mongodb')
    }

    void "test dependencies are present for gradle"() {
        when:
        String template = new BuildBuilder(beanContext)
                .features(["embedded-mongodb"])
                .language(Language.GROOVY)
                .render()

        then:
        template.contains("testRuntimeOnly(\"org.grails.plugins:embedded-mongodb:2.0.0.M1\")")
    }

    void "test config"() {
        when:
        GeneratorContext ctx = buildGeneratorContext(["embedded-mongodb"])

        then:
        ctx.getConfiguration().containsKey("environments.test.grails.mongodb.version")
        ctx.getConfiguration().containsKey("environments.test.grails.mongodb.port")
    }

    void "test gradle properties"() {
        when:
        def output = generate(["embedded-mongodb"])
        def properties = output["gradle.properties"]

        then:
        properties
        properties.contains("embedded-mongo.version=2.2.0")
    }
}
