package org.grails.forge.api.create.github

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpHeaders
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Header
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import org.grails.forge.client.github.oauth.AccessToken
import org.grails.forge.client.github.oauth.GitHubOAuthOperations

@Requires(property = 'spec.name', value = 'GitHubCreateControllerSpec')
@Controller("/login/oauth")
class GitHubOauthMockedController implements GitHubOAuthOperations {

    @Override
    @Post(value = "/access_token")
    AccessToken accessToken(
            @Header(HttpHeaders.USER_AGENT) String userAgent,
            @QueryValue("client_id") String clientId,
            @QueryValue("client_secret") String clientSecret,
            @QueryValue String code,
            @QueryValue String state) {
        return new AccessToken("foo", "repo,user", "123")
    }
}
