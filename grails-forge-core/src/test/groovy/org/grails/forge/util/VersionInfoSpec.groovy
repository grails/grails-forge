package org.grails.forge.util

import spock.lang.Specification

class VersionInfoSpec extends Specification {

    void "test get dependency version"() {
        given:
        def version = VersionInfo.getDependencyVersion("grails")

        expect:
        version.key == 'grails.version'
        version.value
    }
}
