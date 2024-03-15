package org.grails.forge.feature.database

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.ApplicationType
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.feature.Features
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class HibernateGormSpec extends ApplicationContextSpec implements CommandOutputFixture{

    void "test hibernate gorm features"() {
        when:
        Features features = getFeatures(['gorm-hibernate5'])

        then:
        features.contains("h2")
        features.contains("gorm-hibernate5")
    }

    void "test dependencies are present for gradle"() {
        when:
        final String template = new BuildBuilder(beanContext)
                .features(["gorm-hibernate5"])
                .render()

        then:
        template.contains('implementation("org.grails.plugins:hibernate5")')
        template.contains("runtimeOnly(\"org.apache.tomcat:tomcat-jdbc\")")
        template.contains("runtimeOnly(\"com.h2database:h2\")")
    }

    void "test dependencies are present for buildSrc"() {
        when:
        final String template = new BuildBuilder(beanContext)
                .features(["gorm-hibernate5"])
                .renderBuildSrc()

        then:
        template.contains('implementation("org.grails.plugins:hibernate5:8.1.0")')
    }

    void "test buildSrc is present for buildscript dependencies"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        final def buildSrcBuildGradle = output["buildSrc/build.gradle"]

        expect:
        buildSrcBuildGradle != null
        buildSrcBuildGradle.contains("implementation(\"org.grails.plugins:hibernate5:8.1.0\")")

    }

    void "test config"() {
        when:
        GeneratorContext ctx = buildGeneratorContext(['gorm-hibernate5'])

        then:
        ctx.configuration.containsKey("dataSource.url")
        ctx.configuration.containsKey("dataSource.pooled")
        ctx.configuration.containsKey("dataSource.jmxExport")
        ctx.configuration.containsKey("dataSource.dbCreate")
        ctx.configuration.containsKey("hibernate.cache.queries")
        ctx.configuration.containsKey("hibernate.cache.use_second_level_cache")
        ctx.configuration.containsKey("hibernate.cache.use_query_cache")
    }
}
