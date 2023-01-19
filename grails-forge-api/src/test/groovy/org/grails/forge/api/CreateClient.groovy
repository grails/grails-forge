package org.grails.forge.api

import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import org.grails.forge.options.BuildTool
import org.grails.forge.options.Language

@Client('/')
interface CreateClient {
    @Get(uri = "/create/web/{name}{?features,build,test,lang}", consumes = "application/zip")
    byte[] createApp(
            String name,
            @Nullable List<String> features,
            @Nullable BuildTool build,
            @Nullable TestFramework test,
            @Nullable Language lang
    );

    @Get(uri = "/create/web/{name}{?features,build,test,lang}", consumes = "application/zip")
    HttpResponse<byte[]> createResponse(
            String name,
            @Nullable List<String> features,
            @Nullable BuildTool build,
            @Nullable TestFramework test,
            @Nullable Language lang
    );

    @Get(uri = "/{name}.zip{?features,build,test,lang}", consumes = "application/zip")
    HttpResponse<byte[]> getZip(
            String name,
            @Nullable List<String> features,
            @Nullable BuildTool build,
            @Nullable TestFramework test,
            @Nullable Language lang
    );
}
