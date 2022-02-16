package org.grails.forge

import io.micronaut.context.ApplicationContext
import org.grails.forge.fixture.ContextFixture
import org.grails.forge.fixture.ProjectFixture
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

abstract class BeanContextSpec extends Specification implements ProjectFixture, ContextFixture {

    @Shared
    @AutoCleanup
    ApplicationContext beanContext = ApplicationContext.run()
}
