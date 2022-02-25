package org.grails.forge.feature.assetPipeline

import org.grails.forge.ApplicationContextSpec
import org.grails.forge.BuildBuilder
import org.grails.forge.feature.Features

class AssetPipelineSpec extends ApplicationContextSpec {

    void "test asset-pipeline-grails feature"() {
        when:
        final Features features = getFeatures(["asset-pipeline-grails"])

        then:
        features.contains("asset-pipeline-grails")
    }

    void "test dependencies are present for gradle"() {
        when:
        final String template = new BuildBuilder(beanContext)
                .features(["asset-pipeline-grails"])
                .render()

        then:
        template.contains("id \"com.bertramlabs.asset-pipeline\" version \"3.3.4\"")
        template.contains("runtimeOnly(\"com.bertramlabs.plugins:asset-pipeline-grails:3.3.4\")")
        template.contains('''
assets {
    minifyJs = true
    minifyCss = true
}''')
    }
}
