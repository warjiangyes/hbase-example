package org.example.hbase.monitor.entity;

import lombok.Data;

@Data
public class RegionServerInfo {
    // RegionServer 的 hostname
    private String hostName;
    // "name" : "Hadoop:service=HBase,name=RegionServer,sub=Server"
    // Store个数
    private int storeCount;
    // RegionServer 管理region数量
    private int regionCount;
    // StoreFile 个数
    private int storeFileCount;
    // MemStore 大小
    private double memStoreSize;
    // StoreFile 大小
    private double storeFileSize;
    // 该 RegionServer 所管理的表索引大小
    private double staticIndexSize;
    // 总请求数
    private int totalRequestCount;
    // 读请求数
    private int readRequestCount;
    // 写请求数
    private int writeRequestCount;
    // 合并cell个数
    private int compactedCellsCount;
    // 大合并cell个数
    private int majorCompactedCellsCount;
    // flush到磁盘的大小
    private double flushedCellsSize;
    // 因 MemStore 大于阈值而引发flush的次数
    private int blockedRequestCount;
    // region 分裂请求次数
    private int splitRequestCount;
    // region  分裂成功次数
    private int splitSuccessCount;
    // 请求完成时间超过1000ms的次数
    private int slowGetCount;
    // "name" : "Hadoop:service=HBase,name=RegionServer,sub=IPC"
    // 该 RegionServer 打开的连接数
    private int numOpenConnections;
    // rpc handler 数
    private int numActiveHandler;
    // 收到数据量 GB
    private double sentBytes;
    // 发出数据量 GB
    private double receivedBytes;
}
