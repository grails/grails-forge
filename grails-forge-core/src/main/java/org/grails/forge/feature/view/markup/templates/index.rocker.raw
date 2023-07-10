import grails.core.*
import grails.util.*
import grails.plugins.*
import org.grails.core.artefact.*

model {
	GrailsApplication grailsApplication
	GrailsPluginManager pluginManager
}

xmlDeclaration()
application {
    message "Welcome to Grails!"
    environment Environment.current.name
    appversion grailsApplication.metadata.getApplicationVersion()
    grailsversion GrailsUtil.grailsVersion
    groovyversion GroovySystem.getVersion()
    jvmversion System.getProperty('java.version')
    reloadingagentenabled Environment.reloadingAgentEnabled

    artefacts {
        artefact(type: "controllers") {
         count grailsApplication.getArtefactInfo(ControllerArtefactHandler.TYPE).classesByName.size()
        }
        artefact(type: "domains") {
            count grailsApplication.getArtefactInfo(DomainClassArtefactHandler.TYPE).classesByName.size()
        }
        arefact(type: "services") {
            count grailsApplication.getArtefactInfo(ServiceArtefactHandler.TYPE).classesByName.size()
        }
    }
    controllers {
      grailsApplication.getArtefacts(ControllerArtefactHandler.TYPE).each { GrailsClass c ->
        controller {
            name c.fullName
            logicalPropertyName c.logicalPropertyName
        }
      }
    }
    plugins {
      pluginManager.allPlugins.each { GrailsPlugin p ->
        plugin {
          name p.name
          version p.version
        }
      }
    }
}
