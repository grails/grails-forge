package org.grails.forge.api


import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import org.grails.forge.options.BuildTool
import org.grails.forge.options.Language
import org.grails.forge.util.ZipUtil
import spock.lang.Specification

@MicronautTest
class ZipCreateControllerSpec extends Specification {

    @Inject
    CreateClient client

    @Inject
    MyEventListener eventListener

    void "test default create app command"() {
        when:
        def bytes = client.createApp("test", Collections.emptyList(), null, null, null)

        then:
        ZipUtil.isZip(bytes)
        eventListener.fired
    }

    void "test default create app - bad project name"() {
        when:
        client.createApp("tes%*&*t", Collections.emptyList(), null, null, null)

        then:
        def e = thrown(HttpClientResponseException)
        e.status == HttpStatus.BAD_REQUEST
        e.getResponse().getBody(Map).get()._embedded.errors[0].message.contains("name: must match")
    }

    void "test default create app - missing feature"() {
        when:
        client.createApp("test",['junkkkk'], null, null, null)

        then:
        def e = thrown(HttpClientResponseException)
        e.status == HttpStatus.BAD_REQUEST
        e.getResponse().getBody(Map).get()._embedded.errors[0].message.contains("The requested feature does not exist: junkkkk")
    }

    void "test default create app file name"() {
        when:
        def response = client.createResponse("test", Collections.emptyList(), null, null, null)
        def bytes = response.body()

        then:
        ZipUtil.isZip(bytes)
        response.header(HttpHeaders.CONTENT_DISPOSITION).contains("test.zip")
    }

    void "test get zip"() {
        when:
        def response = client.getZip("test", Collections.emptyList(), null, null, null)
        def bytes = response.body()

        then:
        ZipUtil.isZip(bytes)
        response.header(HttpHeaders.CONTENT_DISPOSITION).contains("test.zip")
    }

    void "test default create app with feature"() {
        when:
        def bytes = client.createApp("test", ['gorm-mongodb'], null, null, null)

        then:
        ZipUtil.containsFileWithContents(bytes, "test/build.gradle", 'mongodb')
    }

    void "test create app with groovy"() {
        when:
        def bytes = client.createApp("test", ['gorm-mongodb'], null, null, Language.GROOVY)

        then:
        ZipUtil.containsFile(bytes, "test/grails-app/init/test/Application.groovy")
    }

    void "test create app with spock"() {
        when:
        def bytes = client.createApp("test", ['gorm-mongodb'], BuildTool.GRADLE, TestFramework.SPOCK, Language.GROOVY)

        then:
        ZipUtil.containsFileWithContents(bytes, "test/build.gradle", "spock")
    }
}
