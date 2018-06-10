package com.enterprisedb.salesengineering.demoapprestapi.services;

import com.enterprisedb.salesengineering.demoapprestapi.data.domain.BlogPost;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enterprisedb.salesengineering.demoapprestapi.data.repositories.BlogPostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository repo;

    private Mapper mapper;

    @Autowired
    public BlogPostService(BlogPostRepository bgRepo, Mapper mapper) {
        this.repo = bgRepo;
        this.mapper = mapper;
    }

    public List<BlogPost> readAll() {

        return map(repo.findAll());

    }

    private List<BlogPost> map(List<BlogPost> entities) {
        if (entities == null || entities.isEmpty()) {
            throw new RuntimeException("No posts found!");
        }

        return entities.stream().map(
                r -> mapper.map(r, BlogPost.class)).collect(
                Collectors.toList());
    }

}
