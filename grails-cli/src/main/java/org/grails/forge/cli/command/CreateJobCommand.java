/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.forge.cli.command;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.core.util.functional.ThrowingSupplier;
import jakarta.inject.Inject;
import java.io.IOException;
import org.grails.forge.application.Project;
import org.grails.forge.cli.CodeGenConfig;
import org.grails.forge.cli.command.templates.job;
import org.grails.forge.feature.other.GrailsQuartz;
import org.grails.forge.io.ConsoleOutput;
import org.grails.forge.io.OutputHandler;
import org.grails.forge.template.RenderResult;
import org.grails.forge.template.RockerTemplate;
import picocli.CommandLine;

@CommandLine.Command(name = CreateJobCommand.NAME, description = "Creates a new Quartz scheduled job")
public class CreateJobCommand extends CodeGenCommand {

    public static final String NAME = "create-job";

    @ReflectiveAccess
    @CommandLine.Parameters(paramLabel = "JOB-NAME", description = "The name of the job")
    String jobName;

    @Inject
    public CreateJobCommand(@Parameter CodeGenConfig config) {
        super(config);
    }

    public CreateJobCommand(CodeGenConfig config,
                            ThrowingSupplier<OutputHandler, IOException> outputHandlerSupplier,
                            ConsoleOutput consoleOutput) {
        super(config, outputHandlerSupplier, consoleOutput);
    }

    @Override
    public boolean applies() {
        return config.getFeatures().contains(GrailsQuartz.FEATURE_NAME);
    }

    @Override
    public Integer call() throws Exception {
        if (!applies()) {
            throw new IllegalArgumentException("Please first select Grails Quartz Plugin");
        }
        final Project project = getProject(jobName);
        final RenderResult result = getTemplateRenderer(project)
            .render(new RockerTemplate("grails-app/jobs/{packagePath}/{className}Job.groovy", job.template(project)), overwrite);
        if (result != null) {
            logRenderResult(result);
        }

        return 0;
    }

    private void logRenderResult(RenderResult result) throws Exception {
        if (result != null) {
            if (result.isSuccess()) {
                out("@|blue ||@ Rendered job to " + result.getPath());
            } else if (result.isSkipped()) {
                warning("Rendering skipped for " + result.getPath() + " because it already exists. Run again with -f to overwrite.");
            } else if (result.getError() != null) {
                throw result.getError();
            }
        }
    }
}
