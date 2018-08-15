package com.anubhavshukla.converter;

import com.anubhavshukla.dataobject.DataNode;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Converts the intermediate storage stage (i.e. DataNode) to YAML string.
 */
public class DataNodeToYamlConverter {

  private static final Logger LOGGER = Logger.getLogger(DataNodeToYamlConverter.class.getName());
  private static final int SPACING_PER_TAB_INDEX = 2;
  private static final String SINGLE_SPACE = " ";
  private static final String COLON = ":";

  public StringBuilder parseDataNode(DataNode startNode) {
    LOGGER.log(Level.FINE, "Received DataNode for conversion to YAML: " + startNode);

    StringBuilder stringBuffer = new StringBuilder();
    startNode.getSubMap().forEach((s, dataNode) -> {
      stringBuffer.append(getSpaceString(dataNode.getTabIndex())).append(s).append(COLON);
      if (dataNode.getValue() != null) {
        stringBuffer.append(SINGLE_SPACE).append(dataNode.getValue())
            .append(System.lineSeparator());
      } else {
        stringBuffer.append(System.lineSeparator());
      }
      stringBuffer.append(parseDataNode(dataNode));
    });
    return stringBuffer;
  }

  private StringBuffer getSpaceString(int tabIndex) {
    StringBuffer stringBuffer = new StringBuffer();
    IntStream.range(0, tabIndex * SPACING_PER_TAB_INDEX)
        .forEach(value -> stringBuffer.append(SINGLE_SPACE));
    return stringBuffer;
  }
}
