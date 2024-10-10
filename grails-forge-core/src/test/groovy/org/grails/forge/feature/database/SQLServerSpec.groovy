package org.grails.forge.feature.database

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.generator.GeneratorContext

class SQLServerSpec extends ApplicationContextSpec {

    void 'test gradle sqlserver feature'() {
        when:
        String template = new BuildBuilder(beanContext)
                .features(['sqlserver'])
                .render()

        then:
        template.contains('runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")')
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
        GeneratorContext ctx = buildGeneratorContext(["gorm-hibernate5", "sqlserver"])

        then:
        ctx.getConfiguration().get("dataSource.driverClassName") == 'com.microsoft.sqlserver.jdbc.SQLServerDriver'
        ctx.getConfiguration().get("dataSource.username") == 'sa'
        ctx.getConfiguration().get("dataSource.password") == ''
        ctx.getConfiguration().get("environments.development.dataSource.url") == 'jdbc:sqlserver://localhost:1433;databaseName=devDb'
        ctx.getConfiguration().get("environments.test.dataSource.url") == 'jdbc:sqlserver://localhost:1433;databaseName=testDb'
        ctx.getConfiguration().get("environments.production.dataSource.url") == 'jdbc:sqlserver://localhost:1433;databaseName=prodDb'
    }

}
