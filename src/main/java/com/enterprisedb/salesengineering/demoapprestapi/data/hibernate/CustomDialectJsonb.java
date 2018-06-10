package com.enterprisedb.salesengineering.demoapprestapi.data.hibernate;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

public class CustomDialectJsonb extends PostgreSQL94Dialect {

    public CustomDialectJsonb() {
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}

