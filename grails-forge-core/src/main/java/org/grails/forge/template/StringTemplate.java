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
package org.grails.forge.template;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StringTemplate implements Template {

    private final String path;
    private final String content;

    public StringTemplate(String path, String content) {
        this.path = path;
        this.content = content;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        outputStream.write(content.getBytes(StandardCharsets.UTF_8));
    }
}
