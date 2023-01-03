package org.grails.forge.create

import org.grails.forge.application.OperatingSystem
import org.grails.forge.cli.command.CreateAppCommand
import org.grails.forge.options.BuildTool
import org.grails.forge.options.Language
import org.grails.forge.utils.CommandSpec
import spock.lang.Ignore

import java.nio.file.Files

class CreateAppSpec extends CommandSpec {


    void "test basic create-app build task"() {
        given:
        generateProject(Language.GROOVY, BuildTool.GRADLE, OperatingSystem.MACOS_ARCH64)

        when:
        String output = executeBuild(BuildTool.GRADLE, "build")

        then:
        output.contains('BUILD SUCCESSFUL')
    }

    void "test create-app contains i18n files"() {
        given:
        generateProject(Language.GROOVY, BuildTool.GRADLE, OperatingSystem.MACOS_ARCH64)

        expect:
        new File(dir, "grails-app/i18n").exists()
    }

    @Override
    String getTempDirectoryPrefix() {
        return "test-app"
    }
}
