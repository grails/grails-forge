# Grails Application Forge

[![Maven Central](https://img.shields.io/maven-central/v/org.grails.forge/grails-forge-core.svg?label=Maven%20Central)](https://search.maven.org/artifact/org.grails.forge/grails-forge-core)
[![Build Status](https://github.com/grails/grails-forge/workflows/Java%20CI/badge.svg)](https://github.com/grails/grails-forge/actions)
[![Revved up by Gradle Enterprise](https://img.shields.io/badge/Revved%20up%20by-Gradle%20Enterprise-06A0CE?logo=Gradle&labelColor=02303A)](https://ge.grails.org/scans)

Generates Grails applications.

## Installation

The CLI application comes in various flavours from a universal Java applications to native applications for Windows, Linux and OS X. These are available for direct download on the [releases page](https://github.com/grails/grails-forge/releases). For installation see the [Grails documentation](https://docs.grails.org/latest/guide/index.html#buildCLI).

If you prefer not to install an application to create Micronaut applications you can do so with `curl` directly from the API:

```bash
$ curl https://forge.grails.org/demo.zip -o demo.zip
$ unzip demo.zip -d demo
$ cd demo
$ ./gradlew run
```

Run `curl https://forge.grails.org` for more information on how to use the API or see the API documentation referenced below.

## UI

If you prefer a browser based user interface you can visit [Grails Forge](https://start.grails.org).

The user interface is [written in React](https://github.com/grails/grails-forge-ui/tree/main/app/launch) and is a static single page application that simply interacts with the https://start.grails.org API.

## API

API documentation for the production instance can be found at:

* [Swagger / OpenAPI Doc](https://start.grails.org/swagger/views/swagger-ui/index.html)
* [RAPI Doc](https://start.grails.org/swagger/views/rapidoc/index.html)

API documentation for the snapshot /development instance can be found at:

* [Swagger / OpenAPI Doc](https://snapshot.grails.org/swagger/views/swagger-ui/index.html)
* [RAPI Doc](https://snapshot.grails.org/swagger/views/rapidoc/index.html)

## Documentation

<!-- See the [Documentation](https://grails.github.io/grails-forge/1.0.x/guide/) for more information. -->

See the [Snapshot Documentation](https://grails.github.io/grails-forge/snapshot/guide/) for the current development docs.

## Snapshots and Releases

Snapshots are automatically published to [Sonatype OSSRH](https://s01.oss.sonatype.org/content/repositories/snapshots/) using [Github Actions](https://github.com/grails/grails-forge/actions).

See the documentation in the [Grails Docs](https://docs.grails.org/latest/guide/index.html#usingsnapshots) for how to configure your build to use snapshots.

Releases are published to Maven Central via [Github Actions](https://github.com/grails/grails-forge/actions).

A release is performed with the following steps:

* [Publish the draft release](https://github.com/grails/grails-forge/releases). There should be already a draft release created, edit and publish it. The Git Tag should start with `v`. For example `v1.0.0`.
* [Monitor the Workflow](https://github.com/grails/grails-forge/actions?query=workflow%3ARelease) to check it passed successfully.
* Celebrate!



