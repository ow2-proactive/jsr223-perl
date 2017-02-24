# jsr223-perl

Execute a perl script; invoke the local perl client through a java JSR 223
script engine.

## Build
Run ./gradlew to create a JAR.

## Usage
Add JAR to classpath; it will make the script engine discoverable with "perl" as a
script engine name. More information [here](http://docs.oracle.com/javase/6/docs/technotes/guides/scripting/programmer_guide/index.html).

## How it works
The script engine takes a Reader or String which contains the perl file.
That perl file will be written to disk and variables will be replaced. After that perl will
be executed with the configuration file.

## Bindings
Bindings are used to have variables inside perl scripts.