package org.example.phoenix.mybatis.test.mybatis;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.zaxxer.hikari.HikariDataSource;

public class HikariDataSourceFactory extends UnpooledDataSourceFactory {

    public HikariDataSourceFactory() {
        this.dataSource = new HikariDataSource();
    }
}
