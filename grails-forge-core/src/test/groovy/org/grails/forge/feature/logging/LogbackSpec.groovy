package org.grails.forge.feature.logging

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.*
import spock.lang.Unroll

class LogbackSpec extends ApplicationContextSpec implements CommandOutputFixture {

    @Unroll
    void "test grails-logging dependency is present for #applicationType application"() {
        when:
        def output = generate(applicationType, new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11))

        then:
        output.containsKey("build.gradle")
        def build = output.get("build.gradle")
        build.contains("implementation(\"org.grails:grails-logging\")")

        where:
        applicationType << ApplicationType.values().toList()
    }

    @Unroll
    void "test logback.xml config file is present for #applicationType application"() {
        when:
        def output = generate(applicationType, new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11))

        then:
        output.containsKey("grails-app/conf/logback.xml")

        where:
        applicationType << ApplicationType.values().toList()
    }
}
