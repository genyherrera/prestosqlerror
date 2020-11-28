package org.gherrera.prestosqlparser.test;

import static org.testng.Assert.assertEquals;

import org.gherrera.prestosqlparser.SchemaAwareQueryAdapter;
import org.testng.annotations.Test;

public class SchemaAwareTest {
  private static final String schemaId = "schema";
  private SchemaAwareQueryAdapter adapter = new SchemaAwareQueryAdapter();

  /**
   * As per presto-parser version 0.244 
   * 
   * The class SqlFormatter method formatName only append double-quotes
   * if your schema does not match the folowing regex "[a-z_][a-z0-9_]*"
   *  
   */
  @Test
  public void testAppendSchemaSmall() {
    String sql = "select * from tableA where entityId='blah'";
    String bound = adapter.rewriteSql(sql, schemaId);
    assertEqualsFormattingStripped(
        bound, "select * from \"schema\".tableA where (entityId = 'blah')");
  }

  @Test
  public void testAppendSchemaUpper() {
    String sql = "select * from tableA where entityId='blah'";
    String bound = adapter.rewriteSql(sql, schemaId.toUpperCase());
    System.out.println(bound);
    assertEqualsFormattingStripped(
        bound, "select * from \"schema\".tableA where (entityId = 'blah')");
  }

  private void assertEqualsFormattingStripped(String sql1, String sql2) {
    assertEquals(
        sql1.replace("\n", " ").toLowerCase().replace("\r", " ").replaceAll(" +", " ").trim(),
        sql2.replace("\n", " ").toLowerCase().replace("\r", " ").replaceAll(" +", " ").trim());
  }
}
