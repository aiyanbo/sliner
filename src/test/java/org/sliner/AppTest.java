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
    public void testWarp() {
        SqlLiner sqlLiner = SqlLinerBuilder.newBuilder().suffix(".xml").build();
        Map<String, String> conditions = new HashMap<String, String>();
        conditions.put("sellerName4Like2a", "Andy");
        conditions.put("sellerType", "1204");
        conditions.put("age4Gt", "23");
        conditions.put("1", "1");// bad condition, it will be exclude the sql.
        List<String> sorters = new ArrayList<String>();
        sorters.add("count4Desc");//bad sort, it will be exclude the sql.
        sorters.add("-level");
        sorters.add("age4Asc");
        SqlWrapper sqlWrapper = sqlLiner.wrap("search", conditions, "AND", sorters);
        System.out.println(sqlWrapper.getSql());
    }

    public void testWarpIdentifier() {
        SqlLiner sqlLiner = SqlLinerBuilder.newBuilder().suffix(".xml").build();
        SqlWrapper sqlWrapper = sqlLiner.wrapIdentifier("search", "0000");
        assertEquals("select * from tb_seller WHERE seller_id = ?", sqlWrapper.getSql());
        assertEquals("0000", sqlWrapper.getValues()[0]);
    }

    public void testMultiValues() {
        SqlLiner sqlLiner = SqlLinerBuilder.newBuilder().suffix(".xml").build();
        Map<String, String> conditions = new HashMap<String, String>();
        conditions.put("sellerName4In", "Andy, Lily");
        conditions.put("sellerType", "1204");
        SqlWrapper sqlWrapper = sqlLiner.wrap("search", conditions, "AND", null);
        System.out.println(sqlWrapper.getSql());
        for (Object value : sqlWrapper.getValues()) {
            System.out.println(value);
        }
    }

}
