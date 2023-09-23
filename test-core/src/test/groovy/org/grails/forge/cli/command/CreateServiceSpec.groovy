package org.grails.forge.cli.command

import io.micronaut.configuration.picocli.PicocliRunner
import org.grails.forge.cli.CodeGenConfig
import org.grails.forge.utils.CommandSpec
import spock.lang.Ignore

class CreateServiceSpec extends CommandSpec {

    @Ignore
    void "test create-service command"() {
        when:
        generateProjectWithDefaults()
        applicationContext.createBean(CodeGenConfig.class, new CodeGenConfig())

        then:
        applicationContext.getBean(CodeGenConfig.class)

        when:
        PicocliRunner.run(CreateServiceCommand.class, applicationContext, "Test")

        then:
        new File(dir, "grails-app/controllers/example/grails/TestService.groovy").exists()

    }

    @Override
    String getTempDirectoryPrefix() {
        return "test-app"
    }
}
