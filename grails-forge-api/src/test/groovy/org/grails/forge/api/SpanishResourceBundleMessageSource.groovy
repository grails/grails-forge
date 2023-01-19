package org.grails.forge.api

import io.micronaut.context.i18n.ResourceBundleMessageSource
import jakarta.inject.Singleton

@Singleton
class SpanishResourceBundleMessageSource extends ResourceBundleMessageSource {

    SpanishResourceBundleMessageSource() {
        super("features", new Locale("es"))
    }
}
