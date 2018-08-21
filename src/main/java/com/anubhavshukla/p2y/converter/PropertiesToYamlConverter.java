package com.anubhavshukla.p2y.converter;

import java.io.File;

/**
 * Convert properties file to YAML string.
 */
public class PropertiesToYamlConverter {

  private final PropertiesToDataNodeConverter propertiesToDataNodeConverter;
  private final DataNodeToYamlConverter dataNodeToYamlConverter;

  public PropertiesToYamlConverter() {
    this.propertiesToDataNodeConverter = new PropertiesToDataNodeConverter();
    this.dataNodeToYamlConverter = new DataNodeToYamlConverter();
  }

  public String toYamlString(String filePath) {
    return dataNodeToYamlConverter.toYamlString(propertiesToDataNodeConverter.toDataNode(filePath));
  }

  public String fileToYamlString(File propertiesFile) {
    return dataNodeToYamlConverter
        .toYamlString(propertiesToDataNodeConverter.fileToDataNode(propertiesFile));
  }

  public String directoryToYamlString(File propertiesDirectory) {
    return dataNodeToYamlConverter
        .toYamlString(propertiesToDataNodeConverter.directoryToDataNode(propertiesDirectory));
  }
}
