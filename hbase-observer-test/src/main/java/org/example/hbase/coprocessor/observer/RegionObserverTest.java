package org.example.hbase.coprocessor.observer;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.coprocessor.BaseRegionObserver;
import org.apache.hadoop.hbase.coprocessor.ObserverContext;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.wal.WALEdit;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RegionObserverTest extends BaseRegionObserver {
    private byte[] columnFamily = Bytes.toBytes("cf");
    private byte[] countCol = Bytes.toBytes("countCol");
    private byte[] unDeleteCol = Bytes.toBytes("unDeleteCol");
    private RegionCoprocessorEnvironment environment;

    // RegionServer打开region前调用
    @Override
    public void start(CoprocessorEnvironment e) throws IOException {
        environment = (RegionCoprocessorEnvironment) e;
    }

    // RegionServer关闭region前调用
    @Override
    public void stop(CoprocessorEnvironment e) throws IOException {

    }

    /*
     * 1. 对 cf:countCol 列进行计数, 每次插入的时候都要与之前的值进行累加
     */
    @Override
    public void prePut(ObserverContext<RegionCoprocessorEnvironment> e, Put put, WALEdit edit, Durability durability) throws IOException {
        if (put.has(columnFamily, countCol)) {
            // 获取 old countCol value
            Result rs = e.getEnvironment().getRegion().get(new Get(put.getRow()));
            int oldNum = 0;
            for (Cell cell : rs.rawCells()) {
                if (CellUtil.matchingColumn(cell, columnFamily, countCol)) {
                    // System.out.println("cell value is:" + Bytes.toString(CellUtil.cloneValue(cell)));
                    oldNum = Integer.parseInt(Bytes.toString(CellUtil.cloneValue(cell)));
                }
            }
            // 获取 new countCol value
            List<Cell> cells = put.get(columnFamily, countCol);
            int newNum = 0;
            for (Cell cell : cells) {
                if (CellUtil.matchingColumn(cell, columnFamily, countCol)) {
                    newNum = Integer.parseInt(Bytes.toString(CellUtil.cloneValue(cell)));
                }
            }
            // sum and update Put实例
            put.addColumn(columnFamily, countCol, Bytes.toBytes(String.valueOf(oldNum + newNum)));
        }
    }

    /*
     * 2. 不能直接删除cf:unDeleteCol, 删除cf:countCol的时候将cf:unDeleteCol一同删除
     */
    @Override
    public void preDelete(ObserverContext<RegionCoprocessorEnvironment> e, Delete delete, WALEdit edit, Durability durability) throws IOException {
        // 判断是否操作 cf 列簇
        List<Cell> cells = delete.getFamilyCellMap().get(columnFamily);
        if (cells == null || cells.size() == 0) {
            return;
        }

        boolean deleteFlag = false;
        for (Cell cell : cells) {
            byte[] qualifier = CellUtil.cloneQualifier(cell);
            if (Arrays.equals(qualifier, unDeleteCol)) {
                throw new IOException("can not delete unDeleteCol column");
            }
            if (Arrays.equals(qualifier, countCol)) {
                deleteFlag = true;
            }
        }
        if (deleteFlag) {
            delete.addColumn(columnFamily, unDeleteCol);
        }
    }
}
