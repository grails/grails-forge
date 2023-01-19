package org.grails.forge.api

import io.micronaut.context.event.ApplicationEventListener
import jakarta.inject.Singleton
import org.grails.forge.api.event.ApplicationGeneratingEvent

@Singleton
class MyEventListener implements ApplicationEventListener<ApplicationGeneratingEvent> {
    boolean fired = false

    @Override
    void onApplicationEvent(ApplicationGeneratingEvent event) {
        fired = true
    }
}
