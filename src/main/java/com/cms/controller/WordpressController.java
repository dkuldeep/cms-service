package com.cms.controller;

import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Post;
import com.cms.repository.PostRepository;
import com.cms.service.ImageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequestMapping("/wordpress")
public class WordpressController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageService imageService;

    @Value("${image.upload.path}")
    private String imageUploadDir;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/import")
    public void import0(@RequestBody String urlString) throws MalformedURLException, URISyntaxException {
        URL url = new URL(urlString);
        ResponseEntity<WordpressPost[]> posts = restTemplate.getForEntity(url.toURI(), WordpressPost[].class);
        for (WordpressPost wordpressPost : posts.getBody()) {
            Post search = new Post();
            search.setSlug(wordpressPost.getSlug());
            if (!postRepository.exists(Example.of(search))) {
                postRepository.saveAndFlush(postPostFunction.apply(wordpressPost));
            }
        }
    }

    @PostMapping("/import/single")
    public void importSingle(@RequestBody String urlString) throws MalformedURLException, URISyntaxException {
        URL url = new URL(urlString);
        ResponseEntity<WordpressPost> post = restTemplate.getForEntity(url.toURI(), WordpressPost.class);
        WordpressPost wordpressPost = post.getBody();
        Post search = new Post();
        search.setSlug(wordpressPost.getSlug());
        if (!postRepository.exists(Example.of(search))) {
            postRepository.saveAndFlush(postPostFunction.apply(wordpressPost));
        }
    }

    private final Function<WordpressPost, Post> postPostFunction = post -> {
        Post request = new Post();

        request.setSlug(post.getSlug());
        request.setTitle(post.getTitle().getRendered());
        request.setCreatedDate(post.getDate());
        request.setUpdatedDate(post.getModified());
        request.setExcerpt(getExcerpt(post.getExcerpt().getRendered()));
        request.setContent(getContent(post.getContent().getRendered()));
        request.setTags(new HashSet<>(0));
        request.setDescription(request.getExcerpt());
        return request;
    };

    private String getExcerpt(String html) {
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("p");
        return !elements.isEmpty() ? elements.get(0).text() : html;
    }

    private String getContent(String html) {
        Document document = Jsoup.parse(html);
        document.getElementsByTag("h2").forEach(element -> element.removeAttr("class"));
        document.getElementsByTag("h3").forEach(element -> element.removeAttr("class"));
        document.getElementsByTag("h4").forEach(element -> element.removeAttr("class"));

        document.getElementsByTag("figure").forEach(fig -> {
            fig.removeAttr("class");
            Elements images = fig.getElementsByTag("img");
            images.forEach(img -> img.attr("src", imageService.saveImage(img.attr("src"))));
            images.forEach(e -> e.removeAttr("class"));
            images.forEach(e -> e.removeAttr("sizes"));
            images.forEach(e -> e.removeAttr("srcset"));
        });

        document.getElementsByTag("pre").forEach(element -> {
            Element code = element.getElementsByTag("code").get(0);
            element.attr("class", code.attr("class"));
            code.removeAttr("class");
        });

        return document.body().html();
    }


}
