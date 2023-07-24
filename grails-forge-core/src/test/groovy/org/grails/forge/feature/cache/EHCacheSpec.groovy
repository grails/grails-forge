package org.grails.forge.feature.cache

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.fixture.CommandOutputFixture
import spock.lang.Unroll

class EHCacheSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void 'test readme.md with feature cache-ehcache contains links to documentation'() {
        when:
        def output = generate(['cache-ehcache'])
        def readme = output["README.md"]

        then:
        readme
        readme.contains("[https://www.ehcache.org/](https://www.ehcache.org/)")
        readme.contains("[Grails EHCache Plugin documentation](https://grails-plugins.github.io/grails-cache-ehcache/latest/)")
    }

    @Unroll
    void 'test gradle cache-ehcache feature'() {
        when:
        String template = new BuildBuilder(beanContext)
                .features(['cache-ehcache'])
                .render()

        then:
        template.contains('implementation("org.grails.plugins:cache-ehcache:3.0.0")')

    }

    void 'test cache-ehcache configuration'() {
        when:
        GeneratorContext commandContext = buildGeneratorContext(['cache-ehcache'])

        then:
        commandContext.configuration.get('grails.cache.ehcache.ehcacheXmlLocation'.toString()) == "classpath:ehcache.xml"
        commandContext.configuration.get('grails.cache.ehcache.lockTimeout'.toString()) == 200
    }

}
