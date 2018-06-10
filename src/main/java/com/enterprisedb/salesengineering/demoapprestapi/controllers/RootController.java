package com.enterprisedb.salesengineering.demoapprestapi.controllers;

import com.enterprisedb.salesengineering.demoapprestapi.data.domain.BlogPost;
import com.enterprisedb.salesengineering.demoapprestapi.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
public class RootController {

    @Autowired
    private BlogPostService service;

    @RequestMapping("/")
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui.html");
    };


    @ApiOperation(value="Gets all blog posts", response=BlogPost.class, responseContainer = "List")
    @RequestMapping(method = RequestMethod.GET, value = "/blogposts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<BlogPost> getBlogPosts() {
        return service.readAll();
    };

}
