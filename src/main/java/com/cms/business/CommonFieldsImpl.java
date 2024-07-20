package com.cms.business;

import com.cms.constant.CommonConstants;
import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Tag;
import com.cms.repository.TagRepository;
import com.cms.service.ImageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.util.List;

public class CommonFieldsImpl implements CommonFieldsRef {
    private final WordpressPost wordpressPost;
    private final ImageService imageService;
    private final TagRepository tagRepository;

    public CommonFieldsImpl(WordpressPost wordpressPost, ImageService imageService, TagRepository tagRepository) {
        this.wordpressPost = wordpressPost;
        this.imageService = imageService;
        this.tagRepository = tagRepository;
    }

    @Override
    public String getSlug() {
        return wordpressPost.getSlug();
    }

    @Override
    public String getDescription() {
        String html = wordpressPost.getExcerpt().getRendered();
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("p");
        return !elements.isEmpty() ? elements.get(0).text() : html;
    }

    @Override
    public LocalDateTime getCreated() {
        return wordpressPost.getDate();
    }

    @Override
    public LocalDateTime getUpdated() {
        return wordpressPost.getModified();
    }

    @Override
    public String getContent() {
        Document document = Jsoup.parse(wordpressPost.getContent().getRendered());
        document.getElementsByTag("h2").forEach(element -> {
            element.removeAttr("id");
            element.removeAttr("class");
        });
        document.getElementsByTag("h3").forEach(element -> {
            element.removeAttr("id");
            element.removeAttr("class");
        });
        document.getElementsByTag("h4").forEach(element -> {
            element.removeAttr("id");
            element.removeAttr("class");
        });

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

    @Override
    public List<Tag> getTags() {
        Tag tag = new Tag();
        tag.setSlug(CommonConstants.WORDPRESS_IMPORT_DEFAULT_TAG);
        return tagRepository.findAll(Example.of(tag));
    }
}
