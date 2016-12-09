# config

[![Build Status](https://travis-ci.org/computelab/config.svg?branch=master)](https://travis-ci.org/computelab/config) [![codecov](https://codecov.io/gh/computelab/config/branch/master/graph/badge.svg)](https://codecov.io/gh/computelab/config)

### Overlaid configuration

Configuration can be defined in many places. Can be passed on command-line as
system properties. Can be read from environment variables. Can be read from
a hidden file in the current user's home directory. Or can be read from source
code.

In the source code, dummy values.
In user's home directory, for local development.
Environment variables and system properties, are for deployed.

Stacked together. [Example]

### Comma-delimited key

Can be read flattened from Java properties.
The same key can be read as JSON path. This is handled transparently.

[Example here]

### DefaultConfig

##### Usage Patterns

1. Write your own Config with the list of keys. To reduce the chance of errors and get intellisense on editors.
2. Check keys on loading so that errors are discovered quickly.
3. Cache the values and add a reload() method if necessary.
4. Load config remotely.

### ConfigBuilder

### Light-weight ConfigReader

### Design Principles

1. No annotations
2. Miniml dependencies. For example, we do not expose any Gson objects on the interface. Instead they are wrapped in and handled transparently for you.
3. Stateless. We do not store any config data. It is a reader -- every call reads realtime data. That opens up the possibility of changing config at runtime.
4. No nulls. Two options are provided. You can either check a boolean flag or catch `ConfigEntryMissingException`.
