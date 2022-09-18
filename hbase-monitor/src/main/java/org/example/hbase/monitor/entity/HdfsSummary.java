package org.example.hbase.monitor.entity;

import lombok.Data;

import java.util.List;

@Data
public class HdfsSummary {

    // "name": "Hadoop:service=NameNode,name=NameNodeInfo"
    // 总空间大小 GB
    private double total;
    // hdfs 已使用的空间大小 GB
    private double dfsUsed;
    // hdfs 已使用空间百分比
    private double percentUsed;
    // hdfs 空闲空间 GB
    private double dfsFree;
    // hdfs 是否处于 safeMode
    private String safeMode;
    // 非 hdfs 空间大小 GB
    private double nonDfsUsed;
    // 集群该 namespace 的 hdfs 使用容量大小
    private double blockPoolUsedSpace;
    // 集群该 namespace 的 hdfs 使用容量所占百分比
    private double percentBlockPoolUsed;
    private double percentRemaining;
    // 集群总的 block 数
    private int totalBlocks;
    // 集群总的文件数
    private int totalFiles;
    // 集群丢失的block数量
    private int missingBlocks;
    // 处于可用状态的 datanode 汇总
    private List<DataNodeInfo> liveDataNodeInfos;
    // 处于不可用状态的 datanode 汇总
    private List<DataNodeInfo> deadDataNodeInfos;
    // "name": "Hadoop:service=NameNode,name=FSNamesystemState"
    // 处于可用状态的 datanode 数量
    private int numLiveDataNodes;
    // 处于不可用状态的 datanode 数量
    private int numDeadDataNodes;
    // 坏盘的数量
    private int volumeFailuresTotal;

    public void printInfo() {

        System.out.println("HDFS SUMMARY INFO");
        System.out.printf("totalBlocks:%s\ntotalFiles:%s\nnumLiveDataNodes:%s\n", totalBlocks, totalFiles, numLiveDataNodes);
        liveDataNodeInfos.forEach(node -> {
            System.out.printf("nodeName:%s\nnumBlocks:%s\n", node.getNodeName(), node.getNumBlocks());
        });
        System.out.println("----------------------");
    }

}
