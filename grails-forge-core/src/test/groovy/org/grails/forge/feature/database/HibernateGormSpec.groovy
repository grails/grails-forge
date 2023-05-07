package org.grails.forge.feature.database

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.ApplicationType
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.feature.Features
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.BuildTool
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Language
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
        final String template = new BuildBuilder(beanContext, BuildTool.GRADLE)
                .features(["gorm-hibernate5"])
                .render()

        then:
        template.contains('implementation("org.grails.plugins:hibernate5")')
        template.contains('implementation("org.hibernate:hibernate-core:5.6.15.Final")')
        template.contains("runtimeOnly(\"org.glassfish.web:el-impl:2.2.1-b05\")")
        template.contains("runtimeOnly(\"org.apache.tomcat:tomcat-jdbc\")")
        template.contains("runtimeOnly(\"com.h2database:h2\")")
        template.contains("runtimeOnly(\"javax.xml.bind:jaxb-api:2.3.1\")")
    }

    void "test dependencies are present for buildSrc"() {
        when:
        final String template = new BuildBuilder(beanContext, BuildTool.GRADLE)
                .features(["gorm-hibernate5"])
                .renderBuildSrc()

        then:
        template.contains('implementation("org.grails.plugins:hibernate5:8.0.0-M1")')
    }

    void "test buildSrc is present for buildscript dependencies"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11))
        final def buildSrcBuildGradle = output["buildSrc/build.gradle"]

        expect:
        buildSrcBuildGradle != null
        buildSrcBuildGradle.contains("implementation(\"org.grails.plugins:hibernate5:8.0.0-M1\")")

    }

    void "test config"() {
        when:
        GeneratorContext ctx = buildGeneratorContext(['gorm-hibernate5'])

        then:
        ctx.configuration.containsKey("dataSource.url")
        ctx.configuration.containsKey("dataSource.pooled")
        ctx.configuration.containsKey("dataSource.jmxExport")
        ctx.configuration.containsKey("hibernate.hbm2ddl.auto")
        ctx.configuration.containsKey("hibernate.cache.queries")
        ctx.configuration.containsKey("hibernate.cache.use_second_level_cache")
        ctx.configuration.containsKey("hibernate.cache.use_query_cache")
    }
}
