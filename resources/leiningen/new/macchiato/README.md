## Welcome to {{name}}

### Prequisites

[Node.js](https://nodejs.org/en/) needs to be installed to run the application.

### running in development mode

run the following command in the terminal to install NPM modules and start Figwheel:

```
lein build
```

run `node` in another terminal:

```
node target/out/{{name}}.js
```

#### configuring the REPL

Once Figwheel and node are running, you can connect to the remote REPL at `localhost:7000`.
Type `(cljs)` in the REPL to connect to Figwheel ClojureSrcipt REPL.


### building the release version

```
lein release
```

Run the release version:

```
node target/release/{{name}}.js
```
