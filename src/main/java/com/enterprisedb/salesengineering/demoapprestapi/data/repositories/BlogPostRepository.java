package com.enterprisedb.salesengineering.demoapprestapi.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import com.enterprisedb.salesengineering.demoapprestapi.data.domain.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, UUID>{

}
