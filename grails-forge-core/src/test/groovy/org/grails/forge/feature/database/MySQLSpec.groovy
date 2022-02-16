package org.grails.forge.feature.database

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.generator.GeneratorContext

class MySQLSpec extends ApplicationContextSpec {

    void 'test gradle mysql feature'() {
        when:
        String template = new BuildBuilder(beanContext)
                .features(['mysql'])
                .render()

        then:
        template.contains('runtimeOnly("mysql:mysql-connector-java")')
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
        GeneratorContext ctx = buildGeneratorContext(["gorm-hibernate5", "mysql"])

        then:
        ctx.getConfiguration().get("dataSource.url") == 'jdbc:mysql://localhost:3306/db'
        ctx.getConfiguration().get("dataSource.driverClassName") == 'com.mysql.cj.jdbc.Driver'
        ctx.getConfiguration().get("dataSource.username") == 'root'
        ctx.getConfiguration().get("dataSource.password") == ''
    }
}
