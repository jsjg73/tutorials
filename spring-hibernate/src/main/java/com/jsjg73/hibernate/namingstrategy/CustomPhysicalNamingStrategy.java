package com.jsjg73.hibernate.namingstrategy;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomPhysicalNamingStrategy implements PhysicalNamingStrategy {

	@Override
	public Identifier toPhysicalCatalogName(final Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convertToSnakeCase(name);
	}

	@Override
	public Identifier toPhysicalSchemaName(final Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convertToSnakeCase(name);
	}

	@Override
	public Identifier toPhysicalTableName(final Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convertToSnakeCase(name);
	}

	@Override
	public Identifier toPhysicalSequenceName(final Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convertToSnakeCase(name);
	}

	@Override
	public Identifier toPhysicalColumnName(final Identifier name, JdbcEnvironment jdbcEnvironment) {
		return convertToSnakeCase(name);
	}
	
	private Identifier convertToSnakeCase(final Identifier name) {
		if(name == null)
			return name;
        final String regex = "([a-z])([A-Z])";
        final String replacement = "$1_$2";
        final String newName = name.getText()
          .replaceAll(regex, replacement)
          .toLowerCase();
        return Identifier.toIdentifier(newName);
    }
}
