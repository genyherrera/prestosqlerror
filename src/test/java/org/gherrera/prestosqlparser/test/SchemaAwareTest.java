package org.gherrera.prestosqlparser.test;

import static org.testng.Assert.assertEquals;

import org.gherrera.prestosqlparser.SchemaAwareQueryAdapter;
import org.testng.annotations.Test;

public class SchemaAwareTest {
	    private static final String schemaId = "SCHEMA";
	    private SchemaAwareQueryAdapter adapter = new SchemaAwareQueryAdapter();

	    @Test
	    public void testAppendSchemaA() {
	        String sql = "select * from tableA";
	        String bound = adapter.rewriteSql(sql, schemaId);
	        assertEqualsFormattingStripped(bound,
	                     "select * from \"SCHEMA\".tableA");
	    }
	    
	    private void assertEqualsFormattingStripped(String sql1, String sql2) {
	        
	        assertEquals(sql1.replace("\n", " ").toLowerCase().replace("\r", " ").replaceAll(" +", " ").trim(),
	                     sql2.replace("\n", " ").toLowerCase().replace("\r", " ").replaceAll(" +", " ").trim());
	        
	    }
}
