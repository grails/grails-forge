package org.grails.forge.feature.view.json

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.ApplicationType
import org.grails.forge.feature.Features
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.*
import spock.lang.Unroll

class ViewMarkupSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void "test views-json feature"() {
        when:
        final Features features = getFeatures(["views-markup"])

        then:
        features.contains("grails-web")
        features.contains("views-markup")
        !features.contains("views-json")
    }

    void "test dependencies are present for Gradle"() {
        when:
        final String template = new BuildBuilder(beanContext)
                .features(["views-markup"])
                .render()

        then:
        template.contains("id \"org.grails.grails-web\"")
        template.contains("id \"org.grails.plugins.views-markup\"")
        template.contains("implementation(\"org.grails.plugins:views-markup\"")
        !template.contains("id \"org.grails.plugins.views-json\"")
    }

    void "test default gml views are present"() {
        when:
        final def output = generate(ApplicationType.REST_API, new Options(TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11), ["views-markup"])

        then:
        output.containsKey("grails-app/views/application/index.gml")
        output.containsKey("grails-app/views/error.gml")
        output.containsKey("grails-app/views/notFound.gml")
        output.containsKey("grails-app/views/errors/_errors.gml")
        output.containsKey("grails-app/views/object/_object.gml")
    }

    @Unroll
    void "test views-markup gradle plugins and dependencies are present for #applicationType application"() {
        when:
        final def output = generate(applicationType, new Options(TestFramework.SPOCK, BuildTool.GRADLE, JdkVersion.JDK_11), ["views-markup"])
        final String build = output['build.gradle']

        then:
        build.contains("id \"org.grails.grails-web\"")
        build.contains("id \"org.grails.plugins.views-markup\"")
        build.contains("implementation(\"org.grails.plugins:views-markup\"")
        !build.contains("id \"org.grails.plugins.views-json\"")
        !build.contains("implementation(\"org.grails.plugins:views-json\"")
        !build.contains("implementation(\"org.grails:views-json-testing-support\"")

        where:
        applicationType << [ApplicationType.REST_API]
    }
}
