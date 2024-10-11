package org.grails.forge.feature.grailsWrapper

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework
import spock.lang.Unroll

class GrailsWrapperSpec extends ApplicationContextSpec implements CommandOutputFixture {

    @Unroll
    void "test grails-wrapper.jar, grailsw and grailsw.bat files are present for #applicationType application"() {
        when:
        def output = generate(applicationType, new Options(TestFramework.SPOCK, JdkVersion.DEFAULT_OPTION), ['grails-wrapper'])

        then:
        output.containsKey("grails-wrapper.jar")
        output.containsKey("grailsw")
        output.containsKey("grailsw.bat")

        where:
        applicationType << ApplicationType.values().toList()
    }
}
