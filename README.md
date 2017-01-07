# config

[![Build Status](https://travis-ci.org/computelab/config.svg?branch=master)](https://travis-ci.org/computelab/config) [![codecov](https://codecov.io/gh/computelab/config/branch/master/graph/badge.svg)](https://codecov.io/gh/computelab/config)

## Usage

### Example app

Suppose we have an app code-named "Foo". Foo runs as a web API that binds to a *port*. Under the hood, Foo uses another web API called "Bar" which has an *endpoint* and requires an *access token* to access it.

Foo has the following configurable items: a port number, Bar's endpoint, and Bar's access token.

### DefaultConfigBuilder

The easiest is to use `DefaultConfigBuilder`.

```java
// Build a config using an app name
// The app name will be used for the hidden folder uder the user's home directory
final String appName = "foo";
final Config config = new DefaultConfigBuilder(appName).build();

// Read the config entries
final int port = config.getAsInt("port");
final String barEndpoint = config.get("bar.endpoint");
final String barToken = config.get("bar.token");
```

In order for this to work, configure the Foo app like below,

#### 1. Source code resource

In the source code, under `src/main/resources`, add a file `app.properties` with the following content,

    port = 8080
    bar.endpoint = http://localhost:8089/
    bar.token = dummy

The values in this `app.properties` are default values. Ideally the values are sufficient to launch the app locally.

Note sensitive data should be avoided here. For example, token is given a dummy value here. In a local stack, this token may be checked against the same dummy value or may not be checked at all.

This configuration file is optional but is highly recommended. The goal is for every developer to run the code locally with zero custom configuration needed.

#### 2. User's home directory

In the user's home directory, create a hidden folder `.foo`. Make sure only the user can access the folder. In this hidden folder, create the file `app.properties`,

    bar.endpoint = https://beta.bar.net:8089/
    bar.token = 9C6B9F9B9DCB1

Note that in this file, it overwrites the default values of `bar.endpoint` and `bar.token`.

This file is optional. It is mainly for local integration test against remote resources.

#### 3. Environment variables

When the app is deployed, configuration values can be passed in as environment variables. This further overwrites values defined in the source code and in user's home directory. For example, a staging environment,

    export BAR_ENDPOINT="https://staging.bar.net:8089/"
    export BAR_TOKEN="3C53CD2D51F71"

#### 4. System properties

Alternatively, system properties can be passed to the VM on the `java` command. For example, set the port to 443,

    java -jar foo.jar -Dport=443

### Custom ConfigBuilder

(To be written)

### JSON ConfigBuilder

(To be written)

### Define your own config

(To be written)

### Lightweight ConfigReader

(To be written)
