package org.grails.forge.feature.view


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

class GrailsGspSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test grails-gsp feature"() {
        when:
        final Features features = getFeatures([ "grails-gsp"])

        then:
        features.contains("grails-web")
        features.contains("grails-gsp")
    }

    void "test dependencies are present for Gradle"() {
        when:
        final String template = new BuildBuilder(beanContext)
            .features(["grails-gsp"])
            .render()

        then:
        template.contains("id \"org.grails.grails-web\"")
        template.contains("id \"org.grails.grails-gsp\"")
        template.contains("implementation(\"org.grails.plugins:gsp\")")
    }

    void "test gsp configuration"() {
        when:
        final GeneratorContext ctx = buildGeneratorContext(["grails-gsp"])

        then:
        ctx.getConfiguration().containsKey("grails.views.gsp.encoding")
        ctx.getConfiguration().containsKey("grails.views.gsp.htmlcodec")
        ctx.getConfiguration().containsKey("grails.views.gsp.codecs.expression")
        ctx.getConfiguration().containsKey("grails.views.gsp.codecs.scriptlet")
        ctx.getConfiguration().containsKey("grails.views.gsp.codecs.taglib")
        ctx.getConfiguration().containsKey("grails.views.gsp.codecs.staticparts")
    }

    void "test default views are present"() {
        when:
        final def output = generate(ApplicationType.DEFAULT, new Options(Language.GROOVY, TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11))
        
        then:
        output.containsKey("grails-app/views/index.gsp")
        output.containsKey("grails-app/views/error.gsp")
        output.containsKey("grails-app/views/notFound.gsp")
    }

}
