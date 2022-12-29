package org.grails.forge.fixture

import io.micronaut.context.BeanContext
import io.micronaut.inject.qualifiers.Qualifiers
import org.grails.forge.application.ApplicationType
import org.grails.forge.application.ContextFactory
import org.grails.forge.application.OperatingSystem
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.build.dependencies.Source
import org.grails.forge.feature.AvailableFeatures
import org.grails.forge.feature.Feature
import org.grails.forge.feature.FeatureContext
import org.grails.forge.feature.Features
import org.grails.forge.feature.validation.FeatureValidator
import org.grails.forge.io.ConsoleOutput
import org.grails.forge.options.BuildTool
import org.grails.forge.options.Language
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

import java.util.function.Consumer

trait ContextFixture {

    abstract BeanContext getBeanContext()

    String getGradleAnnotationProcessorScope(Language language, Source source = Source.MAIN) {
        if (language == Language.GROOVY) {
            if (source == Source.MAIN) {
                return "compileOnly"
            } else if (source == Source.TEST) {
                return "testCompileOnly"
            }
        }
    }

    Features getFeatures(List<String> features,
                         Language language = null,
                         TestFramework testFramework = null,
                         BuildTool buildTool = BuildTool.GRADLE,
                         ApplicationType applicationType = ApplicationType.WEB) {
        Options options = new Options(language, testFramework, buildTool)
        return getFeatures(features, options, applicationType)
    }

    Features getFeatures(List<String> features,
                         Options options,
                         ApplicationType applicationType = ApplicationType.WEB) {
        FeatureContext featureContext = buildFeatureContext(features, options, applicationType)
        featureContext.processSelectedFeatures()
        Set<Feature> finalFeatures = featureContext.getFinalFeatures(ConsoleOutput.NOOP)
        beanContext.getBean(FeatureValidator).validatePostProcessing(featureContext.getOptions(), applicationType, finalFeatures)
        return new Features(buildGeneratorContext(features, options, applicationType), finalFeatures, options)
    }

    FeatureContext buildFeatureContext(List<String> selectedFeatures,
                                       Options options = new Options(null, null, BuildTool.GRADLE),
                                       ApplicationType applicationType = ApplicationType.WEB) {

        AvailableFeatures availableFeatures = beanContext.getBean(AvailableFeatures, Qualifiers.byName(applicationType.name))

        ContextFactory factory = beanContext.getBean(ContextFactory)

        factory.createFeatureContext(availableFeatures,
                selectedFeatures,
                applicationType,
                options,
                OperatingSystem.LINUX)
    }

    GeneratorContext buildGeneratorContext(List<String> selectedFeatures,
                                           Options options = new Options(null, null, BuildTool.GRADLE),
                                           ApplicationType applicationType = ApplicationType.WEB) {
        if (this instanceof ProjectFixture) {
            ContextFactory factory = beanContext.getBean(ContextFactory)
            FeatureContext featureContext = buildFeatureContext(selectedFeatures, options, applicationType)
            GeneratorContext generatorContext = factory.createGeneratorContext(((ProjectFixture) this).buildProject(), featureContext, ConsoleOutput.NOOP)
            generatorContext.applyFeatures()
            return generatorContext
        } else {
            throw new IllegalStateException("Cannot get generator context without implementing ProjectFixture")
        }
    }

    GeneratorContext buildGeneratorContext(List<String> selectedFeatures,
                                           Consumer<GeneratorContext> mutate,
                                           Options options = new Options(null, null, BuildTool.GRADLE),
                                           ApplicationType applicationType = ApplicationType.WEB) {
        if (this instanceof ProjectFixture) {
            ContextFactory factory = beanContext.getBean(ContextFactory)
            FeatureContext featureContext = buildFeatureContext(selectedFeatures, options, applicationType)
            GeneratorContext generatorContext = factory.createGeneratorContext(((ProjectFixture) this).buildProject(), featureContext, ConsoleOutput.NOOP)
            mutate.accept(generatorContext)
            generatorContext.applyFeatures()
            return generatorContext
        } else {
            throw new IllegalStateException("Cannot get generator context without implementing ProjectFixture")
        }
    }

}
