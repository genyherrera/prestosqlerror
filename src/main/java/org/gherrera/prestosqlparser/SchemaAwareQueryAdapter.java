package org.gherrera.prestosqlparser;

import java.util.Optional;

import com.facebook.presto.sql.SqlFormatter;
import com.facebook.presto.sql.parser.ParsingOptions;
import com.facebook.presto.sql.parser.SqlParser;

public class SchemaAwareQueryAdapter {
    // Inspired from
    // https://github.com/prestodb/presto/tree/master/presto-parser/src/test/java/com/facebook/presto/sql/parser

    private static final SqlParser SQL_PARSER = new SqlParser();

    public String rewriteSql(String sqlStatement, String schemaId) {
        com.facebook.presto.sql.tree.Statement statement = SQL_PARSER.createStatement(sqlStatement, ParsingOptions.builder().build());
        SchemaAwareQueryVisitor visitor = new SchemaAwareQueryVisitor(schemaId);
        statement.accept(visitor, null);
        return SqlFormatter.formatSql(statement, Optional.empty());
    }

}
