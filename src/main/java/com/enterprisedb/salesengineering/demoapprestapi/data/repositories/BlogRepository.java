package com.enterprisedb.salesengineering.demoapprestapi.data.repositories;

import com.enterprisedb.salesengineering.demoapprestapi.data.entities.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BlogRepository extends JpaRepository<BlogEntity, UUID>{

}
