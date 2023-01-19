package org.grails.forge.api

import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import org.grails.forge.application.ApplicationType
import org.grails.forge.options.BuildTool
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Language

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Client('/diff')
interface DiffClient {
    @Get(uri = "/{type}/feature/{feature}{?lang,build,test,javaVersion,name}",
            consumes = MediaType.TEXT_PLAIN)
    String diffFeature(
            @NotNull ApplicationType type,
            @Nullable String name,
            @NotBlank @NonNull String feature,
            @Nullable BuildTool build,
            @Nullable TestFramework test,
            @Nullable Language lang,
            @Nullable JdkVersion javaVersion);

    @Get(uri = "/{type}/{name}{?features,lang,build,test,javaVersion}", consumes = MediaType.TEXT_PLAIN)
    String diffApp(ApplicationType type,
                   String name,
                   @Nullable List<String> features,
                   @Nullable BuildTool build,
                   @Nullable TestFramework test,
                   @Nullable Language lang,
                   @Nullable JdkVersion javaVersion);
}
