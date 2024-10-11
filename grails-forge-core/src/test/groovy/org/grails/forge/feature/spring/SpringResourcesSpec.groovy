package org.grails.forge.feature.spring

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework
import spock.lang.Unroll

class SpringResourcesSpec extends ApplicationContextSpec implements CommandOutputFixture {

    @Unroll
    void "test /conf/spring/resources.groovy config file is present for #applicationType application"() {
        when:
        def output = generate(applicationType, new Options(TestFramework.SPOCK, JdkVersion.DEFAULT_OPTION))

        then:
        output.containsKey("grails-app/conf/spring/resources.groovy")

        where:
        applicationType << ApplicationType.values().toList()
    }
}
