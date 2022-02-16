package org.grails.forge.feature.cache

import org.grails.forge.BeanContextSpec
import spock.lang.Ignore
import spock.lang.Unroll

class CacheSpec extends BeanContextSpec {

    @Ignore("There is only one cache implementation right now")
    void 'test there can only be one cache feature'() {
        when:
        getFeatures(["cache-ehcache"])

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message.contains("There can only be one of the following features selected")
    }

    @Unroll
    void 'cache can be selected with #otherCache feature'() {
        when:
        getFeatures(["cache", otherCache])

        then:
        noExceptionThrown()

        where:
        otherCache << ["cache-ehcache"]
    }

}
