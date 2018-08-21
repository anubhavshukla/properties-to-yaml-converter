package com.anubhavshukla.p2y.dataobject;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Intermediate transient object for converting Properties to Yaml. Stores key values in nested
 * format.
 */
public class DataNode {

  private int tabIndex;
  private SortedMap<String, DataNode> subMap;
  private String value;

  public DataNode(int tabIndex) {
    this.tabIndex = tabIndex;
    initSubMap();
  }

  private DataNode() {
    initSubMap();
  }

  public static DataNode getInstance() {
    return new DataNode();
  }

  public DataNode getOrPut(String key, int tabIndex) {
    if (subMap.containsKey(key)) {
      return subMap.get(key);
    } else {
      DataNode keyNode = new DataNode(tabIndex);
      subMap.put(key, keyNode);
      return keyNode;
    }
  }

  private void initSubMap() {
    if (subMap == null) {
      subMap = new TreeMap<>();
    }
  }

  public int getTabIndex() {
    return tabIndex;
  }

  public SortedMap<String, DataNode> getSubMap() {
    return subMap;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public DataNode getNode(String key) {
    return subMap.get(key);
  }

  @Override
  public String toString() {
    return "DataNode{" +
        "tabIndex=" + tabIndex +
        ", subMap=" + subMap +
        ", value='" + value + '\'' +
        '}';
  }
}
