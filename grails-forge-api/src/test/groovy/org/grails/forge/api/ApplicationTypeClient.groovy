package org.grails.forge.api

import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.client.annotation.Client
import org.grails.forge.application.ApplicationType

@Client('/')
interface ApplicationTypeClient extends ApplicationTypeOperations {

    @Get("/application-types/{type}/features")
    @Header(name = HttpHeaders.ACCEPT_LANGUAGE, value = "es")
    FeatureList spanishFeatures(ApplicationType type);

    @Get("/application-types/{type}/features/default")
    @Header(name = HttpHeaders.ACCEPT_LANGUAGE, value = "es")
    FeatureList spanishDefaultFeatures(ApplicationType type);
}
