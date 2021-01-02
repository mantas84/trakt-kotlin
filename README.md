trakt-kotlin
==========

#### 🔨 Work in progress 🔨

![CI](https://github.com/mantas84/trakt-kotlin/workflows/CI/badge.svg?branch=main)

A fork from [Trakt-java](https://github.com/UweTrottmann/trakt-java/)

A ~~Java~~ Kotlin wrapper around the [Trakt v2 API](http://docs.trakt.apiary.io/) using [retrofit 2](https://square.github.io/retrofit/).

[Pull requests](CONTRIBUTING.md) are welcome.

Trakt methods are grouped into service objects which can be centrally
managed by a `TraktV2` instance. It will act as a factory for
all of the services and will automatically initialize them with your
API key (OAuth client id) and optionally a given user access token.

## Usage

TBA

## Quality

Not production-ready. Most code auto-converted to Kotlin from Java. More idiomatic kotlin is coming.

## Most notable changes

- [x] Kotlin
- [x] Replace Gson with Moshi
- [ ] Add coroutines

## License

This work by [Mantas](https://www.mantasboro.dev) is licensed under the [Apache License 2.0](LICENSE.txt).

[Contributors](https://github.com/mantas84/trakt-kotlin/graphs/contributors) and changes are tracked by Git.

Do not just copy, make it better.
