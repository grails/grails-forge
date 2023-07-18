package org.grails.forge.feature.spring

import org.grails.forge.BeanContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework
import spock.lang.Unroll

class SpringBootSpec extends BeanContextSpec implements CommandOutputFixture {

    @Unroll
    void "test spring boot starter dependencies for #applicationType application"() {
        when:
        def output = generate(
                applicationType,
                new Options(TestFramework.SPOCK, JdkVersion.JDK_11)
        )
        final String build = output['build.gradle']

        then:
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter-actuator\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter-logging\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter-validation\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-autoconfigure\")")

        where:
        applicationType << [ApplicationType.WEB, ApplicationType.REST_API, ApplicationType.WEB_PLUGIN]
    }

    void "test spring boot starter dependencies for PLUGIN application"() {
        when:
        def output = generate(
                ApplicationType.PLUGIN,
                new Options(TestFramework.SPOCK, JdkVersion.JDK_11)
        )
        final String build = output['build.gradle']

        then:
        !build.contains("implementation(\"org.springframework.boot:spring-boot-starter\")")
        !build.contains("implementation(\"org.springframework.boot:spring-boot-starter-actuator\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter-logging\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter-validation\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-autoconfigure\")")
        !build.contains("implementation(\"org.springframework.boot:spring-boot-starter-tomcat\")")
    }
}
