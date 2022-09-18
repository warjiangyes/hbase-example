package org.example.hbase.monitor.entity;

import lombok.Data;

@Data
public class DataNodeInfo {
    // datanode 的hostname
    private String nodeName;
    // datanode 的ip地址
    private String nodeAddress;
    // datanode 的上次链接数量
    private int lastContact;
    // datanode 上hdfs的已用空间 GB
    private double usedSpace;
    // datanode 的状态
    private String adminState;
    // datanode 上非hdfs的空间大小 GB
    private double nonDfsUsedSpace;
    // datanode 上的总空间大小
    private double capacity;
    // datanode 的block
    private int numBlocks;
    private double remaining;
    private double blockPoolUsed;
    private double blockPoolUsedParent;
}
