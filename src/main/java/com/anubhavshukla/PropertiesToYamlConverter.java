package com.anubhavshukla;

import com.anubhavshukla.converter.DataNodeToYamlConverter;
import com.anubhavshukla.converter.PropertiesToDataNodeConverter;
import java.io.File;
import java.util.logging.Logger;

public class PropertiesToYamlConverter {

  private static final Logger LOGGER = Logger.getLogger(PropertiesToYamlConverter.class.getName());

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
