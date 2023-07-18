package org.grails.forge.create

import org.grails.forge.application.OperatingSystem
import org.grails.forge.utils.CommandSpec

class CreateAppSpec extends CommandSpec {


    void "test basic create-app build task"() {
        given:
        generateProject(OperatingSystem.MACOS_ARCH64)

        when:
        final String output = executeGradle("build").getOutput()

        then:
        output.contains('BUILD SUCCESSFUL')
    }

    void "test create-app contains i18n files"() {
        given:
        generateProject(OperatingSystem.MACOS_ARCH64)

        expect:
        new File(dir, "grails-app/i18n").exists()
    }

    @Override
    String getTempDirectoryPrefix() {
        return "test-app"
    }
}
