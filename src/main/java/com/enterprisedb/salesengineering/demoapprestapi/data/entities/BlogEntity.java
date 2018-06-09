package com.enterprisedb.salesengineering.demoapprestapi.data.entities;

//import com.basaki.example.postgres.jsonb.data.strategy.DirtyStateIdentifiable;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.enterprisedb.salesengineering.demoapprestapi.data.models.*;

@Entity
@Table(name = "blogs", schema = "public")
public class BlogEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "data", nullable = false)
    @Type(type = "import com.enterprisedb.salesengineering.demoapprestapi.data.usertype.JsonbUserType",
            parameters = {@Parameter(name = "className",
                    value = "import com.enterprisedb.salesengineering.demoapprestapi.data.Blog")})
    private Blog blog;

}
