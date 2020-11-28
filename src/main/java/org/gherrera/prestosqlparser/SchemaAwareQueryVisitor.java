package org.gherrera.prestosqlparser;

import java.lang.reflect.Field;
import java.util.List;

import com.facebook.presto.sql.tree.DefaultTraversalVisitor;
import com.facebook.presto.sql.tree.QualifiedName;
import com.facebook.presto.sql.tree.Table;

public class SchemaAwareQueryVisitor extends DefaultTraversalVisitor<Void, Void> {
    private String schemaId;

    public SchemaAwareQueryVisitor(String schemaId) {
        super();
        this.schemaId = schemaId;
    }

    /**
     * The customer can type:
     * [table name]
     * [schema].[table name]
     * [catalog].[schema].[table name]
     */
    @Override
    protected Void visitTable(Table node, Void context) {
        List<String> parts = node.getName().getParts();
        // [table name] -> is the only one we need to modify, so let's check by parts.size() ==1
        if (parts.size() == 1) {
        	try {
                Field privateStringField = Table.class.getDeclaredField("name");
                privateStringField.setAccessible(true);
                QualifiedName qualifiedName = QualifiedName.of("\""+schemaId+"\"",node.getName().getParts().get(0));
                privateStringField.set(node, qualifiedName);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                throw new SecurityException("Unable to execute query");
            }
        }
        return null;
        
    }
}
