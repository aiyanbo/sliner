package org.sliner;

import junit.framework.TestCase;
import org.sliner.domain.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
public class JpaMappingTest extends TestCase {
    private SqlLiner sqlLiner;

    public void setUp() {
        sqlLiner = SqlLinerBuilder.newBuilder().classes(Seller.class).build();
    }

    public void testWarp() {
        Map<String, String> conditions = new HashMap<String, String>();
        conditions.put("seller_name4Like2a", "Andy");
        conditions.put("seller_type", "1204");
        conditions.put("age4Gt", "23");
        conditions.put("1", "1");// bad condition, it will be exclude the sql.
        List<String> sorters = new ArrayList<String>();
        sorters.add("count4Desc");//bad sort, it will be exclude the sql.
        sorters.add("-level");
        sorters.add("age4Asc");
        SqlWrapper sqlWrapper = sqlLiner.wrap("seller", conditions, "AND", sorters);
        System.out.println(sqlWrapper.getSql());
    }

    public void testWarpIdentifier() {
        SqlWrapper sqlWrapper = sqlLiner.wrapIdentifier("seller", "0000");
        assertEquals("select * from tb_seller WHERE seller_id = ?", sqlWrapper.getSql());
        assertEquals("0000", sqlWrapper.getValues()[0]);
    }

    public void testWarpIdentifierAndArguments() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("partitionId", "1");
        SqlWrapper sqlWrapper = sqlLiner.wrapIdentifier("search", "0000", arguments);
        assertTrue(sqlWrapper.getSql().contains("seller_id"));
        assertTrue(sqlWrapper.getSql().contains("partition_id"));
        assertEquals("0000", sqlWrapper.getValues()[0]);
    }

    public void testMultiValues() {
        Map<String, String> conditions = new HashMap<String, String>();
        conditions.put("seller_name4In", "Andy, Lily");
        conditions.put("seller_type", "1204");
        SqlWrapper sqlWrapper = sqlLiner.wrap("seller", conditions, "AND", null);
        System.out.println(sqlWrapper.getSql());
        for (Object value : sqlWrapper.getValues()) {
            System.out.println(value);
        }
    }

}
