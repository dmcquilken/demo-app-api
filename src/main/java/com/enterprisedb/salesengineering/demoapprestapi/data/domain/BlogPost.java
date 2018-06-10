package com.enterprisedb.salesengineering.demoapprestapi.data.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import java.util.Map;


@Entity
@Table(name = "blog_posts", schema = "public")
public class BlogPost implements Serializable {

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    @GeneratedValue(generator = "uuid-gen")
    @Type(type = "pg-uuid")
    private UUID id;

    @Column(name = "data", nullable = false)
    @Type(type = "com.enterprisedb.salesengineering.demoapprestapi.data.hibernate.JsonbUserType")
    private Map<String, String> data;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public BlogPost() {
    }

}
