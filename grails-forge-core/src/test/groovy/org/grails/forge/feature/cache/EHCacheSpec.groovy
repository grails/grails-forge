package org.grails.forge.feature.cache

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.fixture.CommandOutputFixture
import spock.lang.Unroll

class EHCacheSpec extends ApplicationContextSpec implements CommandOutputFixture {

    void 'test readme.md with feature cache-ehcache contains links to micronaut docs'() {
        when:
        def output = generate(['cache-ehcache'])
        def readme = output["README.md"]

        then:
        readme
        readme.contains("https://docs.grails.org/6.0.0/guide/index.html")
        readme.contains("https://docs.grails.org/6.0.0/api/index.html")
        readme.contains("https://guides.grails.org/index.html")
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
