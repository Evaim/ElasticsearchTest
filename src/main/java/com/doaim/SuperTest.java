package com.doaim;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.AnsiSqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import org.junit.After;
import org.junit.Before;

/**
 * Created by Doaim on 16/5/13.
 */
public class SuperTest {
    private DruidPlugin dp;
    ActiveRecordPlugin arp;
    @Before
    public void before(){
        dp = new DruidPlugin(
                "jdbc:sqlserver://192.168.0.5:1433; DatabaseName=WIND", "sa",
                "zaq1@WSX1");
        arp = new ActiveRecordPlugin(dp);
        arp.setDialect(new AnsiSqlDialect());
        dp.start();
        arp.start();
    }
    @After
    public void after(){
        arp.stop();
        dp.stop();
    }
}
