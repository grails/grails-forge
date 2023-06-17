package org.grails.forge.features.scaffolding

import org.grails.forge.options.BuildTool
import org.grails.forge.options.Language
import org.grails.forge.utils.CommandSpec

class ScaffoldingSpec extends CommandSpec {

    void "test generate-controller command"() {

        given:
        generateProject(Language.DEFAULT_OPTION)

        when:
        final String output = executeBuild(BuildTool.DEFAULT_OPTION, "build")

        then:
        output.contains("BUILD SUCCESSFUL")


    }

    @Override
    String getTempDirectoryPrefix() {
        'testapp'
    }
}
