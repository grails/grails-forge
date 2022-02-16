package org.grails.forge.feature.logging

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder

class LogbackSpec extends ApplicationContextSpec {

    void "test grails-logging dependency is present"() {
        when:
        String template = new BuildBuilder(beanContext)
                .features(["logback"])
                .render()

        then:
        template.contains("implementation(\"org.grails:grails-logging\")")
    }
}
