package org.grails.forge.api

import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Header
import io.micronaut.http.client.annotation.Client
import org.grails.forge.application.ApplicationType
import org.grails.forge.options.BuildTool
import org.grails.forge.options.GormImpl
import org.grails.forge.options.ServletImpl
import org.grails.forge.options.JdkVersion

@Client('/')
interface ApplicationTypeClient extends ApplicationTypeOperations {

    @Get("/application-types/{type}/features{?gorm,servlet,build,test,javaVersion}")
    @Header(name = HttpHeaders.ACCEPT_LANGUAGE, value = "es")
    FeatureList spanishFeatures(ApplicationType type,
                                @Nullable BuildTool build,
                                @Nullable TestFramework test,
                                @Nullable GormImpl gorm,
                                @Nullable ServletImpl servlet,
                                @Nullable JdkVersion javaVersion);

    @Get("/application-types/{type}/features/default{?gorm,servlet,build,test,javaVersion}")
    @Header(name = HttpHeaders.ACCEPT_LANGUAGE, value = "es")
    FeatureList spanishDefaultFeatures(ApplicationType type,
                                       @Nullable BuildTool build,
                                       @Nullable TestFramework test,
                                       @Nullable GormImpl gorm,
                                       @Nullable ServletImpl servlet,
                                       @Nullable JdkVersion javaVersion);
}
