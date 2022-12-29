package org.grails.forge

import io.micronaut.context.ApplicationContext
import io.micronaut.context.BeanContext
import org.grails.forge.application.ApplicationType
import org.grails.forge.application.Project
import org.grails.forge.application.generator.GeneratorContext
import org.grails.forge.build.dependencies.CoordinateResolver
import org.grails.forge.build.gradle.GradleBuild
import org.grails.forge.build.gradle.GradleBuildCreator
import org.grails.forge.feature.Features
import org.grails.forge.feature.build.gradle.templates.buildGradle
import org.grails.forge.feature.build.gradle.templates.buildSrcBuildGradle
import org.grails.forge.fixture.ContextFixture
import org.grails.forge.fixture.ProjectFixture
import org.grails.forge.options.BuildTool
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Language
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework

class BuildBuilder implements ProjectFixture, ContextFixture {

    private BuildTool buildTool
    private List<String> features
    private Language language
    private TestFramework testFramework
    private ApplicationType applicationType
    private JdkVersion jdkVersion
    private Project project
    private ApplicationContext ctx
    private GradleBuildCreator gradleDependencyResolver

    BuildBuilder(ApplicationContext ctx) {
        this.ctx = ctx
        this.buildTool = BuildTool.GRADLE
        this.language = Language.GROOVY
    }

    BuildBuilder(ApplicationContext ctx, BuildTool buildTool) {
        this.ctx = ctx
        this.buildTool = buildTool
    }

    BuildBuilder features(List<String> features) {
        this.features = features
        this
    }

    BuildBuilder language(Language language) {
        this.language = language
        this
    }

    BuildBuilder testFramework(TestFramework testFramework) {
        this.testFramework = testFramework
        this
    }

    BuildBuilder applicationType(ApplicationType applicationType) {
        this.applicationType = applicationType
        this
    }

    BuildBuilder jdkVersion(JdkVersion jdkVersion) {
        this.jdkVersion = jdkVersion
        this
    }

    BuildBuilder project(Project project) {
        this.project = project
        this
    }

    String render() {
        List<String> featureNames = this.features ?: []
        Language language = this.language ?: Language.DEFAULT_OPTION
        TestFramework testFramework = this.testFramework ?: language.defaults.test
        ApplicationType type = this.applicationType ?: ApplicationType.WEB
        Project project = this.project ?: buildProject()
        JdkVersion jdkVersion = this.jdkVersion ?: JdkVersion.JDK_11

        Options options = new Options(language, testFramework, buildTool, jdkVersion)
        Features features = getFeatures(featureNames, options, type)

        if (buildTool.isGradle()) {
            GradleBuild build = gradleBuild(options, features, project, type)
            return buildGradle.template(type, project, features, build).render().toString()
        }
        null
    }

    String renderBuildSrc() {
        List<String> featureNames = this.features ?: []
        Language language = this.language ?: Language.DEFAULT_OPTION
        TestFramework testFramework = this.testFramework ?: language.defaults.test
        ApplicationType type = this.applicationType ?: ApplicationType.WEB
        Project project = this.project ?: buildProject()
        JdkVersion jdkVersion = this.jdkVersion ?: JdkVersion.JDK_11

        Options options = new Options(language, testFramework, buildTool, jdkVersion)
        Features features = getFeatures(featureNames, options, type)

        if (buildTool.isGradle()) {
            GradleBuild build = gradleBuild(options, features, project, type)
            return buildSrcBuildGradle.template(type, project, features, build).render().toString()
        }
        null
    }

    private GradleBuildCreator getGradleDependencyResolver() {
        if (gradleDependencyResolver == null) {
            gradleDependencyResolver = ctx.getBean(GradleBuildCreator)
        }
        gradleDependencyResolver
    }

    GradleBuild gradleBuild(Options options, Features features, Project project, ApplicationType type) {
        GeneratorContext ctx = createGeneratorContextAndApplyFeatures(options, features, project, type)
        getGradleDependencyResolver().create(ctx)
    }

    GeneratorContext createGeneratorContextAndApplyFeatures(Options options, Features features, Project project, ApplicationType type) {
        GeneratorContext ctx = new GeneratorContext(project, type, options, null, features.features, ctx.getBean(CoordinateResolver))
        features.features.each {feat -> feat.apply(ctx)}
        ctx
    }

    @Override
    BeanContext getBeanContext() {
        ctx
    }
}
