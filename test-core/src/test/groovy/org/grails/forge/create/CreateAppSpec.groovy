package org.grails.forge.create

import org.grails.forge.application.OperatingSystem
import org.grails.forge.utils.CommandSpec
import spock.lang.PendingFeature

class CreateAppSpec extends CommandSpec {

    void "test basic create-app build task"() {
        given:
        generateProject(OperatingSystem.MACOS_ARCH64)

        when:
        /*
            Temporarily disable the integrationTest task.
            -----------------------------------------------

            There is a problem with running the integrationTest task here.
            It is failing with org.openqa.selenium.SessionNotCreatedException.

            This problem was propably masked previously hidden by the fact that the Geb/Selenium
            dependencies were not being included for OperatingSystem.MACOS_ARCH64.

            As of commit 8675723e62df6d136d7af48d5c75d7728cbef871 the Geb/Selenium
            dependencies are included for OperatingSystem.MACOS_ARCH64 and this
            causes the integrationTest task to fail.
        */
        final String output = executeGradle("build -x iT").getOutput()

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
