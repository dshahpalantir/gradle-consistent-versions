/*
 * (c) Copyright 2019 Palantir Technologies Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.gradle.versions

import nebula.test.IntegrationTestKitSpec
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner

abstract class GradleVersionIntegrationTestKitSpec extends IntegrationTestKitSpec {
    String gradleVersion

    final BuildResult runTasks(String... tasks) {
        BuildResult result = with(tasks).build()
        return checkForDeprecations(result)
    }

    final BuildResult runTasksAndFail(String... tasks) {
        BuildResult result = with(tasks).buildAndFail()
        return checkForDeprecations(result)
    }

    final GradleRunner with(String[] tasks) {
        GradleRunner runner = GradleRunner
                .create()
                .withProjectDir(getProjectDir())
                .withArguments(calculateArguments(tasks))
                .withDebug(getDebug())
                .withPluginClasspath()
                .forwardOutput()
        if (gradleVersion != null) {
            runner.withGradleVersion(gradleVersion)
        }
        return runner
    }
}