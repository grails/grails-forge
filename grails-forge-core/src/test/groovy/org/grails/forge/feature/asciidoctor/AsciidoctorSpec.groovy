package org.grails.forge.feature.asciidoctor

import io.micronaut.core.version.SemanticVersion
import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import spock.lang.Unroll

class AsciidoctorSpec extends ApplicationContextSpec {

    @Unroll
    void 'test gradle asciidoctor feature'() {
        when:
        String template = new BuildBuilder(beanContext)
                .features(['asciidoctor'])
                .render()

        then:
        template.contains("apply from: \"gradle/asciidoc.gradle\"")

        when:
        String pluginId = 'org.asciidoctor.jvm.convert'
        String applyPlugin = 'id "' + pluginId + '" version "'

        then:
        template.contains(applyPlugin)

        when:
        Optional<SemanticVersion> semanticVersionOptional = parseCommunityGradlePluginVersion(pluginId, template).map(SemanticVersion::new)

        then:
        noExceptionThrown()
        semanticVersionOptional.isPresent()
    }

}
