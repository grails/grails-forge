package org.grails.forge.options

import spock.lang.Specification
import spock.lang.Unroll

class LanguageSpec extends Specification {

    @Unroll("expected source path: #expected for path: #path , lang: #lang")
    void "getSourcePath returns a path with the correct language extension and source folder"(Language lang,
                                                                                              String expected,
                                                                                              String path) {
        expect:
        expected == lang.getSourcePath(path)

        where:
        lang            || expected
        Language.GROOVY || "src/main/groovy/{packagePath}/{className}.groovy"
        path = '/{packagePath}/{className}'
    }
}
