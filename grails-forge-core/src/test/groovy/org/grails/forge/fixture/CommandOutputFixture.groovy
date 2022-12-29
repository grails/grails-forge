package org.grails.forge.fixture

import groovy.transform.CompileStatic
import io.micronaut.context.BeanContext
import org.grails.forge.application.ApplicationType
import org.grails.forge.application.OperatingSystem
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.application.generator.ProjectGenerator
import org.grails.forge.io.ConsoleOutput
import org.grails.forge.io.MapOutputHandler
import org.grails.forge.options.Options
import org.grails.forge.util.NameUtils

@CompileStatic
trait CommandOutputFixture {
    abstract BeanContext getBeanContext()

    Map<String, String> generate(ApplicationType type, Options options, List<String> features = []) {
        def handler = new MapOutputHandler()
        beanContext.getBean(ProjectGenerator).generate(type,
                NameUtils.parse("example.grails.foo"),
                options,
                OperatingSystem.LINUX,
                features,
                handler,
                ConsoleOutput.NOOP
        )
        handler.getProject()
    }

    Map<String, String> generate(List<String> features = []) {
        generate(ApplicationType.WEB, features)
    }

    Map<String, String> generate(ApplicationType type, List<String> features = []) {
        def handler = new MapOutputHandler()
        Options options = new Options()
        beanContext.getBean(ProjectGenerator).generate(type,
                NameUtils.parse("example.grails.foo"),
                options,
                OperatingSystem.LINUX,
                features,
                handler,
                ConsoleOutput.NOOP
        )
        handler.getProject()
    }

    Map<String, String> generate(ApplicationType type, GeneratorContext generatorContext) {
        def handler = new MapOutputHandler()
        beanContext.getBean(ProjectGenerator).generate(type,
                NameUtils.parse("example.grails.foo"),
                handler,
                generatorContext
        )
        handler.getProject()
    }
}
