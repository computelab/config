# config

[![Build Status](https://travis-ci.org/computelab/config.svg?branch=master)](https://travis-ci.org/computelab/config) [![codecov](https://codecov.io/gh/computelab/config/branch/master/graph/badge.svg)](https://codecov.io/gh/computelab/config) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.computelab/config/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.computelab/config)

## Install

For Maven, add a dependency in `pom.xml`,

```
<dependency>
    <groupId>org.computelab</groupId>
    <artifactId>config</artifactId>
    <version>0.2.0</version>
</dependency>
```

Or, if you use Gradle, in `build.gradle`,

```
compile group: 'org.computelab', name: 'config', version: '0.2.0'
```

## Usage

Suppose we have an app code-named "Foo". Foo runs as a web API that binds to a *port*. Under the hood, Foo uses another web API, called "Bar", which has an *endpoint* and requires an *access token* to access it.

Foo has the following configurable items: a port number, Bar's endpoint, and an access token to access Bar.

### DefaultConfig

The easiest is to use `DefaultConfig`.

```java
// The app name will be used for the hidden folder under the user's home directory
// In this case, ~/.foo/app.properties is the config file in the user's home for "foo" 
// This file is optional though. Details later
final String appName = "foo";
final Config config = DefaultConfig.create(appName);

// Read the config entries
final int port = config.getAsInt("port");
final String barEndpoint = config.get("bar.endpoint");
final String barToken = config.get("bar.token");
```

In order for this to work, configure the Foo app like below,

#### 1. Source code resource

This file is optional but is strongly recommended.

In the source code, under `src/main/resources`, add a file `app.properties` with the following content,

    port = 8080
    bar.endpoint = http://localhost:8089/
    bar.token = dummy

These are Java properties. Note the keys must match those specified in the code. The values in this `app.properties` are default values. Ideally the values are sufficient to launch the app locally.

Note sensitive data should be avoided here. For example, token is given a dummy value here. In a local stack, this token may be checked against the same dummy value or may not be checked at all.

This configuration file is optional but is highly recommended. Remember the goal is for every developer to run the code locally with zero custom configuration needed.

#### 2. User's home directory

Each individual developer can have a custom configuration for the Foo app. In the user's home directory, create a hidden folder `.foo`. Make sure only the current user can access the folder and its content. In this hidden folder, create the file `app.properties`,

    bar.endpoint = https://beta.bar.net:8089/
    bar.token = 9C6B9F9B9DCB1

Note that in this file, it overwrites the default values of `bar.endpoint` and `bar.token`.

This file is optional. It is mainly for local integration test against remote resources.

#### 3. Environment variables

When the app is deployed, configuration values can be passed in as environment variables. This further overwrites values defined in the source code and in user's home directory. For example, when deploying Foo to a staging environment, the staging environment can set the following environment variables,

    export BAR_ENDPOINT="https://staging.bar.net:8089/"
    export BAR_TOKEN="3C53CD2D51F71"

This is usually where sensitive data are passed to the app. As shown here, we are passing in a real token to access Bar.

#### 4. System properties

Alternatively, system properties can be passed to the VM on the `java` command. For example, setting the port to 443,

    java -jar foo.jar -Dport=443

### Custom ConfigBuilder

(To be written)

### JSON ConfigBuilder

(To be written)

### Define your own config

#### 1. Hide the keys

Tire of typing key names? Hide them by creating your own config type. The following example wraps `DefaultConfigBuilder` into FooConfig,

```java
public class FooConfig() {

    private static final String APP_NAME = "foo";
    private final Config config;

    public FooConfig() {
        config = new DefaultConfigBuilder(APP_NAME).build();
    }

    public int port() {
        return config.getAsInt("port");
    }

    public String barEndpoint() {
        return config.get("bar.endpoint");
    }

    public String barEndpoint() {
        return config.get("bar.token");
    }
}
```

#### 2. Refresh the config by providing a callback

Example, when changing config flags at runtime.

(To be written)

### Lightweight ConfigReader

(To be written)
