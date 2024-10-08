package org.grails.forge.feature.asciidoctor

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.feature.Features
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class AsciidoctorSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void 'test gradle asciidoctor feature'() {
        when:
        final Features features = getFeatures(["asciidoctor"])

        then:
        features.contains("asciidoctor")
    }

    void "test asciidoctor gradle configurations"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11), ["asciidoctor"])
        final def buildGradle = output["build.gradle"]
        final def settingGradle = output["settings.gradle"]

        expect:
        settingGradle.contains("id \"org.asciidoctor.jvm.convert\" version \"4.0.1\"")
        buildGradle.contains("apply from: \"gradle/asciidoc.gradle\"")
    }

    void "test asciidoctor gradle configurations"() {
        given:
        final def output = generate(ApplicationType.WEB, new Options(TestFramework.SPOCK, JdkVersion.JDK_11), ["asciidoctor"])
        final def asciidocGradle = output["gradle/asciidoc.gradle"]

        expect:
        asciidocGradle.contains("""asciidoctorj {
    version '2.1.0'
    modules {
        diagram {
            version '1.5.18'
        }
    }

    options doctype: "book", ruby: "erubis"

    attributes "sourcedir": "src/docs/asciidoc",
               "source-highlighter": "coderay",
               "toc": "left",
               "idprefix": "",
               "idseparator": "-",
               "icons": "font",
               "setanchors": "",
               "listing-caption": "",
               "imagesdir": "images",
               "project-version": "\$project.version",
               "revnumber": "\$project.version"
}
""")
    }

}
