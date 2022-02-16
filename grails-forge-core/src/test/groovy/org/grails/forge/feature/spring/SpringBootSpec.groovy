package org.grails.forge.feature.spring

import org.grails.forge.BeanContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.BuildTool
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Language
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class SpringBootSpec extends BeanContextSpec implements CommandOutputFixture {

    void "test spring boot starter"() {
        when:
        def output = generate(
                ApplicationType.DEFAULT,
                new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11)
        )
        final String build = output['build.gradle']

        then:
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter-actuator\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter-logging\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter-validation\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-autoconfigure\")")
        build.contains("implementation(\"org.springframework.boot:spring-boot-starter-tomcat\")")
    }
}
