package org.grails.forge.fixture

import org.grails.forge.application.Project
import org.grails.forge.util.NameUtils

trait ProjectFixture {

    Project buildProject(String name = 'example.grails.foo') {
        NameUtils.parse(name)
    }

}