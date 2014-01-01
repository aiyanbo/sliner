package org.sliner;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    public void test() {
        SqlLiner sqlLiner = SqlLinerBuilder.newBuilder().suffix(".xml").build();
        Map<String, String> conditions = new HashMap<String, String>();
        conditions.put("sellerName4Like2a", "Andy");
        conditions.put("sellerType", "1204");
        conditions.put("age4Gt", "23");
        conditions.put("1", "1");// bad condition, it will be exclude the sql.
        List<String> sorters = new ArrayList<String>();
        sorters.add("count4Desc");//bad sort, it will be exclude the sql.
        sorters.add("level4Desc");
        sorters.add("age4Asc");
        SqlWrapper sqlWrapper = sqlLiner.wrap("search", conditions, "AND", sorters);
        assertEquals("select * from tb_seller WHERE seller_type = ? AND seller_name like ? ORDER BY level DESC,age ASC",
                sqlWrapper.getSql());
        System.out.println(sqlWrapper.getSql());
        assertEquals("select * from tb_seller WHERE seller_type = ? AND seller_name like ? ORDER BY level DESC,age ASC", sqlWrapper.getSql());
    }
}
