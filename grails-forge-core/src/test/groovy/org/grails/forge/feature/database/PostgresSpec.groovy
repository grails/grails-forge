package org.grails.forge.feature.database

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.generator.GeneratorContext

class PostgresSpec extends ApplicationContextSpec {

    void 'test gradle postgres feature'() {
        when:
        String template = new BuildBuilder(beanContext)
                .features(['postgres'])
                .render()

        then:
        template.contains('runtimeOnly("org.postgresql:postgresql")')
    }

    void "test there can only be one of DatabaseDriverFeature"() {
        when:
        getFeatures(beanContext.getBeansOfType(DatabaseDriverFeature)*.name)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message.contains("There can only be one of the following features selected")
    }

    void "test config"() {
        when:
        GeneratorContext ctx = buildGeneratorContext(["gorm-hibernate5", "postgres"])

        then:
        ctx.getConfiguration().get("dataSource.url") == 'jdbc:postgresql://localhost:5432/postgres'
        ctx.getConfiguration().get("dataSource.driverClassName") == 'org.postgresql.Driver'
        ctx.getConfiguration().get("dataSource.username") == 'postgres'
        ctx.getConfiguration().get("dataSource.password") == ''
    }
}
