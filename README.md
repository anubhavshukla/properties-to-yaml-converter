[Properties to YAML Converter](https://gitlab.com/anubhavshukla/properties-to-yaml-converter) 
===

This library allows you to convert Properties files into Yaml files. The library can be used through Java code or from command line.

The library can be used to convert a single Properties file to Yaml file or all the Properties files in a directory to a combined Yaml file. For detailed usage see `com.anubhavshukla.p2y.Main` class.

Library usage:

    String yaml = PropertiesToYamlConverter.toYamlString(filePath);

Command line usage:

    $ java -jar ./dist/properties-to-yaml-converter-<version>.jar -location=<properties-file-path>

