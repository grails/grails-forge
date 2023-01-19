package org.grails.forge.api

import io.micronaut.core.annotation.Nullable
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import org.grails.forge.api.preview.PreviewDTO
import org.grails.forge.application.ApplicationType
import org.grails.forge.options.BuildTool
import org.grails.forge.options.Language

@Client('/preview')
interface PreviewClient  {
    @Get(uri = "/web/{name}{?features,build,test,lang}", consumes = MediaType.APPLICATION_JSON)
    PreviewDTO previewApp(
            ApplicationType type,
            String name,
            @Nullable List<String> features,
            @Nullable BuildTool build,
            @Nullable TestFramework test,
            @Nullable Language lang
    );
}
