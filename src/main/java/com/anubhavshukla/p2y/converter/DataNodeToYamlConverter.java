package com.anubhavshukla.p2y.converter;

import com.anubhavshukla.p2y.dataobject.DataNode;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.System.lineSeparator;

/**
 * Converts the intermediate storage stage (i.e. DataNode) to YAML string.
 */
public class DataNodeToYamlConverter {

  private static final Logger LOGGER = Logger.getLogger(DataNodeToYamlConverter.class.getName());
  private static final int SPACING_PER_TAB_INDEX = 2;
  private static final String SINGLE_SPACE = " ";
  private static final String COLON = ":";
  private static final String LIST_PREFIX = "-";

  public String toYamlString(DataNode startNode) {
    return parseDataNode(startNode).toString();
  }

  private StringBuilder parseDataNode(DataNode startNode) {
    LOGGER.log(Level.FINE, "Received DataNode for conversion to YAML: " + startNode);

    StringBuilder stringBuffer = new StringBuilder();
    startNode.getSubMap().forEach((s, dataNode) -> {
      stringBuffer.append(getSpaceString(dataNode.getTabIndex()))
              .append(s).append(COLON);
      if (Objects.nonNull(dataNode.getValue())) {
        stringBuffer.append(getDataValue(dataNode))
                .append(lineSeparator());
      } else {
        stringBuffer.append(lineSeparator());
      }
      stringBuffer.append(parseDataNode(dataNode));
    });
    return stringBuffer;
  }

  private String getDataValue(DataNode dataNode)
  {
    String dataValue = dataNode.getValue();
    String[] listArr = dataValue.split(",");
    if (listArr.length > 1) {
      return lineSeparator() + Arrays.stream(listArr)
              .map(s -> getSpaceString(dataNode.getTabIndex() + 1) + LIST_PREFIX + SINGLE_SPACE + s)
              .collect(Collectors.joining(lineSeparator()));
    }
    return SINGLE_SPACE + dataNode.getValue();
  }

  private StringBuffer getSpaceString(int tabIndex) {
    StringBuffer stringBuffer = new StringBuffer();
    IntStream.range(0, tabIndex * SPACING_PER_TAB_INDEX)
        .forEach(value -> stringBuffer.append(SINGLE_SPACE));
    return stringBuffer;
  }
}
