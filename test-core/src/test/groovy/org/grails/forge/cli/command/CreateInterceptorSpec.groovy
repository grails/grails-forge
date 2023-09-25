package org.grails.forge.cli.command

import io.micronaut.configuration.picocli.PicocliRunner
import org.grails.forge.cli.CodeGenConfig
import org.grails.forge.utils.CommandSpec
import spock.lang.Ignore

class CreateInterceptorSpec extends CommandSpec {

    @Ignore
    void "test create-interceptor command"() {
        when:
        generateProjectWithDefaults()
        applicationContext.createBean(CodeGenConfig.class, new CodeGenConfig())

        then:
        applicationContext.getBean(CodeGenConfig.class)

        when:
        PicocliRunner.run(CreateInterceptorCommand.class, applicationContext, "test")

        then:
        new File(dir, "grails-app/controllers/example/grails/TestInterceptor.groovy").exists()
        new File(dir, "src/tests/example/grails/TestInterceptorSpec.groovy").exists()

    }

    @Override
    String getTempDirectoryPrefix() {
        return "test-app"
    }
}
