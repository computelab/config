# config

[![Build Status](https://travis-ci.org/computelab/config.svg?branch=master)](https://travis-ci.org/computelab/config) [![codecov](https://codecov.io/gh/computelab/config/branch/master/graph/badge.svg)](https://codecov.io/gh/computelab/config)


Configuration in plain Java.

1. Overlaid configuration

Configuration can be defined in many places. Can be passed on command-line as
system properties. Can be read from environment variables. Can be read from
a hidden file in the current user's home directory. Or can be read from source
code.

In the source code, dummy values.
In user's home directory, for local development.
Environment variables and system properties, are for deployed.

Stacked together. [Example]

2. Comma-delimited key

Can be read flattened from Java properties.
The same key can be read as JSON path. This is handled transparently.

[Example here]

3. DefaultConfig

4. ConfigBuilder

5. Light-weight ConfigReader
