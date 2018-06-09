package com.enterprisedb.salesengineering.demoapprestapi.data.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;


}
