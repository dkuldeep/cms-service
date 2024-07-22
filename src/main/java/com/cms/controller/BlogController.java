package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.dto.DtoMapper;
import com.cms.dto.ImageDto;
import com.cms.dto.request.BlogCreateRequest;
import com.cms.dto.response.BlogResponseDto;
import com.cms.dto.response.BlogSnippetDto;
import com.cms.dto.response.ObjectCreated;
import com.cms.dto.response.ObjectUpdated;
import com.cms.dto.wordpress.WordpressImportRequest;
import com.cms.entity.Blog;
import com.cms.entity.Webpage;
import com.cms.exception.ObjectNotFoundException;
import com.cms.service.BlogService;
import com.cms.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.cms.constant.ErrorMessage.POST_CREATED;

@RestController
@RequestMapping("/blogs")
public class BlogController implements WordpressImport {

    @Autowired
    private BlogService blogService;

    @Value("${image.upload.path}")
    private String imageUploadDir;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public List<BlogResponseDto> getAllBlogs(@RequestParam(required = false) String slug) {
        List<Blog> allBlogs = blogService.getAllBlogs();
        if (Objects.nonNull(slug)) {
            Optional<Blog> optional = allBlogs.stream().filter(blog -> blog.getSlug().equals(slug)).findFirst();
            if (optional.isPresent()) {
                BlogResponseDto dto = DtoMapper.BLOG_TO_DTO.apply(optional.get());
                List<Blog> related = allBlogs.stream().filter(blog1 -> !blog1.getSlug().equals(slug)).toList();
                List<BlogSnippetDto> snippetDtos = related.stream().map(blog1 -> new BlogSnippetDto(blog1.getImage(), blog1.getCreated(), blog1.getHeading(), blog1.getSlug())).toList();
                dto.setRelated(snippetDtos);
                return Collections.singletonList(dto);
            } else {
                throw new ObjectNotFoundException(String.format(ErrorMessage.POST_NOT_FOUND_WITH_SLUG, slug));
            }
        }
        return allBlogs.stream().map(DtoMapper.BLOG_TO_DTO).toList();
    }

    @GetMapping("/{id}")
    public BlogResponseDto findById(@PathVariable int id) {
        Optional<Blog> optional = blogService.findById(id);
        return optional.map(DtoMapper.BLOG_TO_DTO).orElseThrow(() -> new ObjectNotFoundException(String.format(ErrorMessage.POST_BY_ID_NOT_FOUND, id)));
    }

    @GetMapping("latest3")
    public List<BlogResponseDto> latest3() {
        return blogService.getAllBlogs()
                .stream()
                .sorted(Comparator.comparing(Webpage::getCreated))
                .map(DtoMapper.BLOG_TO_DTO)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") int id) {
        blogService.deleteById(id);
    }

    @PostMapping
    public ObjectCreated create(@RequestBody BlogCreateRequest request) {
        Blog blog = blogService.create(request);
        return new ObjectCreated(blog.getId(), POST_CREATED);
    }

    @PutMapping("/{id}")
    public ObjectUpdated updateById(@RequestBody BlogCreateRequest request,
                                    @PathVariable("id") int id) {
        blogService.update(request, id);
        return new ObjectUpdated(ErrorMessage.POST_UPDATED);
    }

    @PatchMapping("{id}")
    public ImageDto uploadImage(@PathVariable("id") Integer id,
                                @RequestParam("file") MultipartFile file) throws IOException {
        String path = blogService.uploadImage(id, file);
        return new ImageDto(path);
    }

    @Override
    @PostMapping("wordpress")
    public ObjectCreated importFromWordpress(@RequestBody WordpressImportRequest request) throws MalformedURLException, URISyntaxException {
        int count = blogService.importFromWordpress(request.getType(), request.getValue());
        return new ObjectCreated(count + " " + POST_CREATED);
    }
}
