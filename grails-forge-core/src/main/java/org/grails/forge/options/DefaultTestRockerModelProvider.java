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
package org.grails.forge.options;

import com.fizzed.rocker.RockerModel;

public class DefaultTestRockerModelProvider implements TestRockerModelProvider {
    private final RockerModel spock;
    private final RockerModel groovyJunit;

    public DefaultTestRockerModelProvider(RockerModel spock,
                                          RockerModel groovyJunit) {
        this.spock = spock;
        this.groovyJunit = groovyJunit;
    }

    @Override
    public RockerModel spock() {
        return spock;
    }

    @Override
    public RockerModel groovyJunit() {
        return groovyJunit;
    }

}
