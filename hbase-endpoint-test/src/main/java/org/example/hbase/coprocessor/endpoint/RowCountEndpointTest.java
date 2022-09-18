package org.example.hbase.coprocessor.endpoint;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.google.protobuf.Service;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.Coprocessor;
import org.apache.hadoop.hbase.CoprocessorEnvironment;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.coprocessor.CoprocessorService;
import org.apache.hadoop.hbase.coprocessor.RegionCoprocessorEnvironment;
import org.apache.hadoop.hbase.regionserver.InternalScanner;
import org.example.hbase.coprocessor.endpoint.GetRowCount.*;

import java.io.IOException;
import java.util.ArrayList;

public class RowCountEndpointTest extends hbaseEndPointTestService implements Coprocessor, CoprocessorService {
    // 单个region的上下文环境信息
    private RegionCoprocessorEnvironment environment;

    // rpc服务，返回本身接口，因为此类实例就是一个服务实现
    @Override
    public Service getService() {
        return this;
    }

    // 协处理器是运行于region中的，每一个region都会加载协处理器
    // 这个方法会在 RegionServer 打开region时候执行（还没有真正打开）
    @Override
    public void start(CoprocessorEnvironment env) throws IOException {
        // 需要检查当前环境是否在region上
        if (env instanceof RegionCoprocessorEnvironment) {
            this.environment = (RegionCoprocessorEnvironment) env;
        } else {
            throw new IOException("Must be loaded on a table region!");
        }
    }

    // 这个方法会在 RegionServer 关闭region时候执行（还没有真正关闭）
    @Override
    public void stop(CoprocessorEnvironment coprocessorEnvironment) throws IOException {

    }

    @Override
    public void getRowCount(RpcController controller, GetRowCount.getRowCountRequest request, RpcCallback<GetRowCount.getRowCountResponse> done) {
        // 单个region上的计算结果值
        int result = 0;

        // 定义返回response
        getRowCountResponse.Builder builder = getRowCountResponse.newBuilder();

        // 进行行数统计
        InternalScanner scanner = null;
        try {
            Scan scan = new Scan();
            scanner = this.environment.getRegion().getScanner(scan);
            ArrayList<Cell> results = new ArrayList<Cell>();
            boolean hasMore = false;
            do {
                hasMore = scanner.next(results);
                // results 一定是一条么？如果不是的话，是否应该 + results.size()
                result ++;
            } while (hasMore);

        } catch (IOException ioe) {
           ioe.printStackTrace();
        } finally {
            if (scanner != null) {
                try {
                    scanner.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        builder.setRowCount(result);
        done.run(builder.build());
    }


}
