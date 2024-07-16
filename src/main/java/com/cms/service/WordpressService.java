package com.cms.service;

import com.cms.dto.wordpress.WordpressPost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WordpressService {

    @Value("${wordpress.cms.posts.url.prefix}")
    private String cmsPostUrl;

    private static final String TYPE_CATEGORY = "CATEGORY";
    private static final String TYPE_SLUG = "SLUG";

    private final RestTemplate restTemplate = new RestTemplate();

    public List<WordpressPost> fetchPosts(String value, String type) throws MalformedURLException, URISyntaxException {
        URL wpUrl;
        switch (type) {
            case TYPE_SLUG -> wpUrl = new URL(cmsPostUrl + "?slug=" + value);
            case TYPE_CATEGORY -> wpUrl = new URL(cmsPostUrl + "?categories=" + value);
            default -> {
                return Collections.emptyList();
            }
        }
        ResponseEntity<WordpressPost[]> posts = restTemplate.getForEntity(wpUrl.toURI(), WordpressPost[].class);
        if (Objects.nonNull(posts.getBody())) {
            return Arrays.stream(posts.getBody()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
