package org.grails.forge.template

import spock.lang.Specification

class YamlTemplateSpec extends Specification {

    void "test yaml output"() {
        Map<String, Object> config = [:]
        config.put("info.app.name", "foo")
        config.put("grails.codegen.defaultPackage", "example")
        config.put("grails.gorm.reactor.events", false)
        config.put("datasources.default.url", "dbURL")
        config.put("datasources.default.className", "h2")
        config.put("jpa.default.properties.hibernate.hbm2ddl", "auto")

        YamlTemplate template = new YamlTemplate("abc", config)
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        template.write(baos)

        expect:
        //single value nested keys get collapsed
        //micronaut. ignores that rule
        baos.toString() == """info.app.name: foo
grails:
  codegen:
    defaultPackage: example
  gorm:
    reactor:
      events: false
datasources:
  default:
    url: dbURL
    className: h2
jpa.default.properties.hibernate.hbm2ddl: auto
"""
    }

    void "test empty yaml output"() {
        Map<String, Object> config = [:]

        YamlTemplate template = new YamlTemplate("abc", config)
        ByteArrayOutputStream baos = new ByteArrayOutputStream()
        template.write(baos)

        expect:
        baos.toString() == "# Place application configuration here"
    }
}
