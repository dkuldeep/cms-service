package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.dto.DtoMapper;
import com.cms.dto.request.BlogCreateRequest;
import com.cms.dto.response.BlogResponseDto;
import com.cms.dto.response.ObjectCreated;
import com.cms.dto.response.ObjectUpdated;
import com.cms.dto.wordpress.WordpressImportRequest;
import com.cms.entity.Blog;
import com.cms.exception.ObjectNotFoundException;
import com.cms.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static com.cms.constant.ErrorMessage.POST_CREATED;

@RestController
@RequestMapping("/blogs")
public class BlogController implements WordpressImport {

    @Autowired
    private BlogService blogService;

    @GetMapping
    public List<BlogResponseDto> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return blogs.stream().map(DtoMapper.BLOG_TO_DTO).toList();
    }

    @GetMapping("/{id}")
    public BlogResponseDto findById(@PathVariable int id) {
        Optional<Blog> optional = blogService.findById(id);
        return optional.map(DtoMapper.BLOG_TO_DTO).orElseThrow(() -> new ObjectNotFoundException(String.format(ErrorMessage.POST_BY_ID_NOT_FOUND, id)));
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

    @Override
    @PostMapping("wordpress")
    public ObjectCreated importFromWordpress(@RequestBody WordpressImportRequest request) throws MalformedURLException, URISyntaxException {
        int count = blogService.importFromWordpress(request.getType(), request.getValue());
        return new ObjectCreated(count + " " + POST_CREATED);
    }
}
