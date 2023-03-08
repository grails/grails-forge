package org.grails.forge.cli.command

import io.micronaut.context.ApplicationContext
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
        generateProject(Language.DEFAULT_OPTION)
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
        generateProject(Language.DEFAULT_OPTION)
        CodeGenConfig codeGenConfig = CodeGenConfig.load(beanContext, dir, ConsoleOutput.NOOP)
        ConsoleOutput consoleOutput = Mock(ConsoleOutput)
        CreateDomainClassCommand command = new CreateDomainClassCommand(codeGenConfig, getOutputHandler(consoleOutput), consoleOutput)

        when:
        command.domainClassName  = 'Pet'
        Integer exitCode = command.call()
        executeGradleCommand("build")

        then:
        exitCode == 0
        testOutputContains("BUILD SUCCESSFUL")
    }
}
