package org.grails.forge.cli.command

import io.micronaut.configuration.picocli.PicocliRunner
import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.Environment
import org.grails.forge.application.WebAvailableFeatures
import org.grails.forge.cli.CommandFixture
import org.grails.forge.cli.CommandSpec
import spock.lang.AutoCleanup
import spock.lang.Shared

class CreateAppCommandSpec extends CommandSpec implements CommandFixture {

    @Shared
    @AutoCleanup
    ApplicationContext ctx = ApplicationContext.run(Environment.CLI)

    @Shared
    @AutoCleanup
    ApplicationContext beanContext = ApplicationContext.run()

    void "test creating project with defaults"() {
        given:
        ByteArrayOutputStream out = new ByteArrayOutputStream()
        System.setOut(new PrintStream(out))

        when:
        PicocliRunner.run(CreateAppCommand, ctx, "foobar")

        then:
        noExceptionThrown()
        out.toString().contains("Application created")
    }

    void "test creating a project with an invalid build tool"() {
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        System.setErr(new PrintStream(baos))

        when:
        PicocliRunner.run(CreateAppCommand, ctx, "temp",  "--build", "xyz")

        then:
        noExceptionThrown()
        baos.toString().contains("Invalid build tool selection: xyz")
    }

    void "test creating a project with an invalid language"() {
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        System.setErr(new PrintStream(baos))

        when:
        PicocliRunner.run(CreateAppCommand, ctx, "temp",  "--lang", "xyz")

        then:
        noExceptionThrown()
        baos.toString().contains("Invalid language selection: xyz")
    }

    void "community and preview features are labelled as such"() {
        given:
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        PrintStream old = System.out
        System.setOut(new PrintStream(baos))

        and:
        def (previewFeature, communityFeature) = beanContext.getBean(WebAvailableFeatures).with {
            [allFeatures.find { it.preview }, allFeatures.find { it.community }]
        }

        when:
        PicocliRunner.run(CreateAppCommand, ctx, "temp", "--list-features")

        then:
        noExceptionThrown()
        baos.toString().contains("$previewFeature.name [PREVIEW]")
        communityFeature == null
//        baos.toString().contains("$communityFeature.name [COMMUNITY]")

        cleanup:
        System.setOut(old)
    }
}
