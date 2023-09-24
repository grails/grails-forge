package org.grails.forge.cli.command

import io.micronaut.configuration.picocli.PicocliRunner
import org.grails.forge.cli.CodeGenConfig
import org.grails.forge.io.ConsoleOutput
import org.grails.forge.utils.CommandSpec
import spock.lang.Ignore

class CreateTagLibSpec extends CommandSpec {

    @Ignore
    void "test create-taglib command"() {
        when:
        generateProjectWithDefaults()
        applicationContext.createBean(CodeGenConfig.class, new CodeGenConfig())

        then:
        applicationContext.getBean(CodeGenConfig.class)

        when:
        PicocliRunner.run(CreateTagLibCommand.class, applicationContext, "simple")

        then:
        new File(dir, "grails-app/taglib/example/grails/SimpleTagLib.groovy").exists()
        new File(dir, "src/tests/example/grails/SimpleTagLibSpec.groovy").exists()

    }

    @Override
    String getTempDirectoryPrefix() {
        return "test-app"
    }
}
