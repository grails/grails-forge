package org.grails.forge.features.scaffolding

import org.gradle.testkit.runner.BuildResult
import org.grails.forge.options.Language
import org.grails.forge.utils.CommandSpec

class ScaffoldingSpec extends CommandSpec {

    void "test generate-controller command"() {

        given:
        generateProjectWithDefaults()

        when:
        final File domainPkg = new File(dir, "grails-app/domain/example/grails")
        domainPkg.mkdirs()
        new File(domainPkg, "Bird.groovy").text = '''package example.grails

class Bird {
    String name
}
'''
        final String output = executeGradle("runCommand", "-Pargs=generate-controller example.grails.Bird").getOutput()

        then:
        output.contains('Rendered template Controller.groovy to destination grails-app/controllers/example/grails/BirdController.groovy')
        output.contains('Rendered template Service.groovy to destination grails-app/services/example/grails/BirdService.groovy')
        output.contains('Rendered template Spec.groovy to destination src/test/groovy/example/grails/BirdControllerSpec.groovy')
        output.contains('Rendered template ServiceSpec.groovy to destination src/test/groovy/example/grails/BirdServiceSpec.groovy')
        new File(dir, "grails-app/controllers/example/grails/BirdController.groovy").exists()
        new File(dir, "grails-app/services/example/grails/BirdService.groovy").exists()
        new File(dir, "src/test/groovy/example/grails/BirdControllerSpec.groovy").exists()
        new File(dir, "src/test/groovy/example/grails/BirdControllerSpec.groovy").exists()
    }

    @Override
    String getTempDirectoryPrefix() {
        'testapp'
    }
}
