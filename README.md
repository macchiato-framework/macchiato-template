# macchiato-template

A [Leiningen](http://leiningen.org/) template for creating ClojureScript applications on top of [Node.js](https://nodejs.org/).

This template creates a skeleton Macchiato based project with a test harness.

## Usage

Create a new application project:

```
lein new macchiato <name> [profile...]
```

### Profiles

- `+browser`
  - adds support for clojurescript in browser

## Contributing & Customizing

Take a look at the open issues, especially ones marked as `help wanted`. If you see one you'd like to address don't hesitate to start a discussion or submit a pull request.

If the template isn't doing quite what you're looking for, it's easy to create a local copy of your own following these simple steps:

```
git clone https://github.com/macchiato-framework/macchiato-template.git
cd macchiato-template
lein install
```

If you feel that your customizations are general enough to be useful for others then please consider making a pull request.

## Requirements

* JDK 1.7+
* Leiningen 2.x

## License

Copyright © 2015 Dmitri Sotnikov

Distributed under the The MIT License (MIT).
