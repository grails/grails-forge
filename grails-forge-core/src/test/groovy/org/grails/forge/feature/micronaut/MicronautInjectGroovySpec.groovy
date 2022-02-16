package org.grails.forge.feature.micronaut

import org.grails.forge.BeanContextSpec
import org.grails.forge.BuildBuilder

class MicronautInjectGroovySpec extends BeanContextSpec {

    void "test dependencies"() {
        when:
        final String template = new BuildBuilder(beanContext)
                .features(["micronaut-inject-groovy"])
                .render()

        then:
        template.contains("compileOnly(\"io.micronaut:micronaut-inject-groovy\")")
        template.contains("testImplementation(\"io.micronaut:micronaut-inject-groovy\")")
    }
}
