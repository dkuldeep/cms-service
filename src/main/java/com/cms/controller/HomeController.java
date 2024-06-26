package com.cms.controller;

import com.cms.dto.PostDto;
import com.cms.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {

    @Autowired
    private PostService postService;

    @GetMapping("")
    public String homePage() {
        return HttpStatus.OK.getReasonPhrase();
    }

    @GetMapping("/privacy")
    public PostDto privacy() {
        return postService.getPostById(52);
    }
}
