package org.grails.forge.feature.view


import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.ApplicationType
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.feature.Features
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework
import spock.lang.Unroll

class GrailsGspSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test grails-gsp feature"() {
        when:
        final Features features = getFeatures(["grails-gsp"])

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

    void "test mime configuration"() {
        when:
        final GeneratorContext ctx = buildGeneratorContext(["grails-gsp"])

        then:
        ctx.getConfiguration().get("grails.mime.disable.accept.header.userAgents") == Arrays.asList("Gecko", "WebKit", "Presto", "Trident")
        ctx.getConfiguration().get("grails.mime.types.all") == "*/*"
        ctx.getConfiguration().get("grails.mime.types.atom") == "application/atom+xml"
        ctx.getConfiguration().get("grails.mime.types.css") == "text/css"
        ctx.getConfiguration().get("grails.mime.types.csv") == "text/csv"
        ctx.getConfiguration().get("grails.mime.types.form") == "application/x-www-form-urlencoded"
        ctx.getConfiguration().get("grails.mime.types.html") == Arrays.asList("text/html", "application/xhtml+xml")
        ctx.getConfiguration().get("grails.mime.types.js") == "text/javascript"
        ctx.getConfiguration().get("grails.mime.types.json") == Arrays.asList("application/json", "text/json")
        ctx.getConfiguration().get("grails.mime.types.multipartForm") == "multipart/form-data"
        ctx.getConfiguration().get("grails.mime.types.pdf") == "application/pdf"
        ctx.getConfiguration().get("grails.mime.types.rss") == "application/rss+xml"
        ctx.getConfiguration().get("grails.mime.types.text") == "text/plain"
        ctx.getConfiguration().get("grails.mime.types.hal") == Arrays.asList("application/hal+json", "application/hal+xml")
        ctx.getConfiguration().get("grails.mime.types.xml") == Arrays.asList("text/xml", "application/xml")
    }

    void "test default views are present"() {
        when:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        
        then:
        output.containsKey("grails-app/views/index.gsp")
        output.containsKey("grails-app/views/error.gsp")
        output.containsKey("grails-app/views/notFound.gsp")
    }

    @Unroll
    void "test grails-gsp gradle plugins and dependencies are present for #applicationType application"() {
        when:
        final def output = generate(applicationType, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        final String build = output['build.gradle']

        then:
        build.contains('id "org.grails.grails-web"')
        build.contains('id "org.grails.grails-gsp"')
        build.contains("implementation(\"org.grails.plugins:gsp\")")

        where:
        applicationType << [ApplicationType.WEB, ApplicationType.WEB_PLUGIN]
    }

    @Unroll
    void "test grails-gsp gradle plugins and dependencies are NOT present for #applicationType application"() {
        when:
        final def output = generate(applicationType, new Options(TestFramework.SPOCK, JdkVersion.JDK_11))
        final String build = output['build.gradle']

        then:
        !build.contains('id "org.grails.grails-gsp"')
        !build.contains("implementation(\"org.grails.plugins:gsp\")")

        where:
        applicationType << [ApplicationType.PLUGIN, ApplicationType.REST_API]
    }

}
