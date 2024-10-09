package org.grails.forge.cli.command

import io.micronaut.context.ApplicationContext
import org.grails.forge.application.ApplicationType
import org.grails.forge.cli.CodeGenConfig
import org.grails.forge.cli.CommandFixture
import org.grails.forge.cli.CommandSpec
import org.grails.forge.io.ConsoleOutput
import org.grails.forge.options.Language
import spock.lang.AutoCleanup
import spock.lang.Shared

class CreateDomainClassCommandSpec extends CommandSpec implements CommandFixture {

    @Shared
    @AutoCleanup
    ApplicationContext beanContext = ApplicationContext.run()

    void "test creating a domain class"() {
        setup:
        generateProject(ApplicationType.WEB)
        CodeGenConfig codeGenConfig = CodeGenConfig.load(beanContext, dir, ConsoleOutput.NOOP)
        ConsoleOutput consoleOutput = Mock(ConsoleOutput)
        CreateDomainClassCommand command = new CreateDomainClassCommand(codeGenConfig, getOutputHandler(consoleOutput), consoleOutput)
        command.domainClassName  = 'Pet'

        when:
        Integer exitCode = command.call()
        File output = new File(dir, "grails-app/domain/example/grails/Pet.groovy")
        File specOutput = new File(dir, "src/test/groovy/example/grails/PetSpec.groovy")

        then:
        exitCode == 0
        output.exists()
        specOutput.exists()
        2 * consoleOutput.out({ it.contains("Rendered domain class") })
    }

    void "test app with domain"() {
        setup:
        generateProject(ApplicationType.WEB)
        CodeGenConfig codeGenConfig = CodeGenConfig.load(beanContext, dir, ConsoleOutput.NOOP)
        ConsoleOutput consoleOutput = Mock(ConsoleOutput)
        CreateDomainClassCommand command = new CreateDomainClassCommand(codeGenConfig, getOutputHandler(consoleOutput), consoleOutput)

        when:
        command.domainClassName  = 'Pet'
        Integer exitCode = command.call()
        /*
            Temporarily disable the integrationTest task.
            -----------------------------------------------

            There is a problem with running the integrationTest task here.
            It is failing with org.openqa.selenium.SessionNotCreatedException.

            This problem was probably masked previously by the fact that the Geb/Selenium
            dependencies were not being included for OperatingSystem.MACOS_ARCH64.

            As of commit 8675723e62df6d136d7af48d5c75d7728cbef871 the Geb/Selenium
            dependencies are included for OperatingSystem.MACOS_ARCH64 and this
            causes the integrationTest task to fail.
        */
        executeGradleCommand("build -x iT")

        then:
        exitCode == 0
        testOutputContains("BUILD SUCCESSFUL")
    }
}
