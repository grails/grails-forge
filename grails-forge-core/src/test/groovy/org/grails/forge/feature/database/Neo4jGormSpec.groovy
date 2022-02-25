package org.grails.forge.feature.database

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.feature.Features
import org.grails.forge.fixture.CommandOutputFixture

class Neo4jGormSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test Mongo gorm features"() {
        when:
        Features features = getFeatures(['gorm-neo4j'])

        then:
        features.contains("gorm-neo4j")
    }

    void "test there can only be one of either MongoDB or Neo4j feature"() {
        when:
        getFeatures(beanContext.getBeansOfType(GormOneOfFeature)*.name)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message.contains("There can only be one of the following features selected")
    }

    void "test dependencies are present for gradle"() {
        when:
        String template = new BuildBuilder(beanContext)
                .features(["gorm-neo4j"])
                .render()

        then:
        template.contains("implementation(\"org.grails.plugins:neo4j\")")
    }

    void "test config"() {
        when:
        GeneratorContext ctx = buildGeneratorContext(['gorm-neo4j'])

        then:
        ctx.configuration.containsKey("grails.neo4j.type")
        ctx.configuration.containsKey("grails.neo4j.location")
    }


}
