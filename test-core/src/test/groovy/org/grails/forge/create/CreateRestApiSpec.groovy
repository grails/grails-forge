package org.grails.forge.create

import org.grails.forge.application.ApplicationType
import org.grails.forge.utils.CommandSpec

class CreateRestApiSpec extends CommandSpec {

    void "test rest-api app with default json-views"() {
        when:
        generateProjectWithDefaults(ApplicationType.REST_API)
        final String output = executeGradle("build").output

        then:
        output.contains('BUILD SUCCESSFUL')
    }

    @Override
    String getTempDirectoryPrefix() {
        return "testrestapi"
    }
}
