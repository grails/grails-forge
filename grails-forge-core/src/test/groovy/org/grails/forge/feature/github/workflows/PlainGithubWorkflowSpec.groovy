package org.grails.forge.feature.github.workflows

import org.grails.forge.BeanContextSpec
import org.grails.forge.application.ApplicationType
import org.grails.forge.feature.github.workflows.plain.PlainGithubWorkflowFeature
import org.grails.forge.fixture.CommandOutputFixture
import org.grails.forge.options.BuildTool
import org.grails.forge.options.JdkVersion
import org.grails.forge.options.Language
import org.grails.forge.options.Options
import org.grails.forge.options.TestFramework
import spock.lang.Unroll

class PlainGithubWorkflowSpec extends BeanContextSpec implements CommandOutputFixture {

    @Unroll
    void 'test github workflow is created for #buildTool'(BuildTool buildTool, String workflowName) {
        when:
        def output = generate(ApplicationType.DEFAULT,
                new Options(Language.GROOVY, TestFramework.SPOCK, buildTool, JdkVersion.JDK_11),
                [PlainGithubWorkflowFeature.NAME])
        def workflow = output[".github/workflows/${workflowName}"]

        then:
        workflow
        workflow.contains("name: Java CI")

        where:
        buildTool | workflowName
        BuildTool.GRADLE | "gradle.yml"
    }

    @Unroll
    void 'test github gradle workflow java version for #version'(JdkVersion version) {
        when:
        def output = generate(ApplicationType.DEFAULT,
                new Options(Language.GROOVY, TestFramework.JUNIT, BuildTool.GRADLE, version),
                [PlainGithubWorkflowFeature.NAME])
        def workflow = output['.github/workflows/gradle.yml']

        then:
        workflow
        workflow.contains("java-version: ${version.majorVersion()}")

        where:
        version << JdkVersion.values()
    }
}
