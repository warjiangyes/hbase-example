package org.example.hbase.monitor.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HbaseSummary {

    //"name": "Hadoop:service=HBase,name=Master,sub=Server"
    private String hmasterNode;
    private String status;
    //处于可用状态的 RegionServer 汇总
    private List<RegionServerInfo> liveRegionServers;
    //处于不可用状态的 RegionServer 汇总
    private List<RegionServerInfo> deadRegionServers;
    //处于可用状态的 RegionServer 数量
    private int numRegionServers;
    //处于不可用状态的 RegionServer 数量
    private int numDeadRegionServers;
    private Date createTime;

    public void printInfo() {
        System.out.println("HBASE SUMMARY INFO");
        System.out.printf("numRegionServers:%d\nnumDeadRegionServers:%d\n", numRegionServers, numDeadRegionServers);
        liveRegionServers.forEach(regionServerInfo -> {
            System.out.printf("hostName:%s\nregionCount:%s%n", regionServerInfo.getHostName(), regionServerInfo.getRegionCount());

        });
        System.out.println("----------------------");

    }
}
