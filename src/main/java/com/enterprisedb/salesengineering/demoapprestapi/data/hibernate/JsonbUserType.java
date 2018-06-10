package com.enterprisedb.salesengineering.demoapprestapi.data.hibernate;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
//import java.io.IOException;
//import java.io.Serializable;
//import java.io.StringWriter;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Types;
//import java.util.Properties;
//import org.hibernate.HibernateException;
//import org.hibernate.engine.spi.SessionImplementor;
//import org.hibernate.usertype.ParameterizedType;
//import org.hibernate.usertype.UserType;


public class JsonbUserType implements UserType {


    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            try {
                final StringWriter writer = new StringWriter();
                MAPPER.writeValue(writer, value);
                writer.flush();
                st.setObject(index, writer.toString(), Types.OTHER);
            } catch (IOException e) {
                throw new RuntimeException(
                        "Failed to convert " + returnedClass() + " to String " + e.getMessage(),
                        e);
            }
        }
    }

    @Override
    public Object deepCopy(Object originalValue) throws HibernateException {
        if (originalValue == null) {
            return null;
        }

        if (!(originalValue instanceof Map)) {
            return null;
        }

        Map<String, String> resultMap = new HashMap<>();

        Map<?, ?> tempMap = (Map<?, ?>) originalValue;
        tempMap.forEach((key, value) -> resultMap.put((String) key, (String) value));

        return resultMap;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        PGobject o = (PGobject) rs.getObject(names[0]);
        String json = rs.getString(names[0]);

        if (json == null) {
            return null;
        }

        try {
            return MAPPER.readValue(json.getBytes("UTF-8"), returnedClass());
        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to convert String to " + returnedClass() + e.getMessage(),
                    e);
        }
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        Object copy = deepCopy(value);

        if (copy instanceof Serializable) {
            return (Serializable) copy;
        }

        throw new SerializationException(String.format("Cannot serialize '%s', %s is not Serializable.", value, value.getClass()), null);
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return deepCopy(original);
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public int hashCode(Object x) throws HibernateException {
        if (x == null) {
            return 0;
        }

        return x.hashCode();
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    @Override
    public Class<?> returnedClass() {
        return Map.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }
}
