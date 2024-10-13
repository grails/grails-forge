package org.grails.forge.options

import spock.lang.Specification

class JdkVersionSpec extends Specification {

    void 'test valueOf with a supported JDK version'() {
        expect:
        JdkVersion.DEFAULT_OPTION == JdkVersion.valueOf(JdkVersion.DEFAULT_OPTION.majorVersion())
    }

    void 'test valueOf when the JDK version does not exist'() {
        when:
        JdkVersion.valueOf(3)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == "Unsupported JDK version: 3. Supported values are " + JdkVersion.SUPPORTED_JDKS
    }
}
