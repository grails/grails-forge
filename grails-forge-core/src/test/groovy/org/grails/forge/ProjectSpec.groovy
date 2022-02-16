package org.grails.forge

import org.grails.forge.application.Project
import org.grails.forge.util.NameUtils
import spock.lang.Specification
import spock.lang.Unroll

class ProjectSpec extends Specification {

    @Unroll
    void "test Project.naturalName(#name) == #result project info"() {
        when:
        Project project = NameUtils.parse(name)

        then:
        project.naturalName == result

        where:
        name                           | result
        "aName"                        | "A Name"
        "name"                         | "Name"
        "firstName"                    | "First Name"
        "URL"                          | "URL"
        "localURL"                     | "Local URL"
        "URLLocal"                     | "URL Local"
        "aURLLocal"                    | "A URL Local"
        "MyDomainClass"                | "My Domain Class"
        "com.myco.myapp.MyDomainClass" | "My Domain Class"
    }
}
