package org.grails.forge.feature.reloading

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.feature.Features
import org.grails.forge.fixture.CommandOutputFixture

class SpringBootDevToolsSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test spring-boot-devtools feature"() {

        when:
        final Features features = getFeatures(["spring-boot-devtools"])

        then:
        features.contains("spring-boot-devtools")

    }

    void "test spring-boot-devtools dependency is present for #applicationType application type"() {

        when:
        def output = generate(applicationType, ["spring-boot-devtools"])
        def build = output["build.gradle"]

        then:
        build.contains("developmentOnly(\"org.springframework.boot:spring-boot-devtools\"")

        where:
        applicationType << [ApplicationType.WEB, ApplicationType.REST_API]
    }

    void "test there can be only one of Reloading feature"() {
        when:
        getFeatures(beanContext.getBeansOfType(ReloadingFeature.class)*.name)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message.contains("There can only be one of the following features selected")
    }
}
