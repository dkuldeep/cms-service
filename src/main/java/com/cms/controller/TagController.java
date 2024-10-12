package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.dto.DtoMapper;
import com.cms.dto.request.TagCreateRequest;
import com.cms.dto.response.ObjectCreated;
import com.cms.dto.response.ObjectUpdated;
import com.cms.dto.response.TagResponse;
import com.cms.dto.response.TagResponseSnippet;
import com.cms.entity.Tag;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.TagRepository;
import com.cms.service.HasSlug;
import com.cms.service.TagService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController implements HasSlug<TagResponse> {

    private final TagRepository tagRepository;
    private final TagService tagService;

    public TagController(TagRepository tagRepository, TagService tagService) {
        this.tagRepository = tagRepository;
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagResponseSnippet> getAll() {
        return tagRepository.findAll().stream().map(DtoMapper.TAG_TO_SNIPPET).toList();
    }

    @Override
    @GetMapping("/slug/{slug}")
    public TagResponse getBySlug(@PathVariable String slug) {
        Optional<Tag> optional = tagService.findBySlug(slug);
        return optional.map(DtoMapper.TAG_TO_DTO)
                .orElseThrow(() -> new ObjectNotFoundException(MessageFormat.format(ErrorMessage.TAG_NOT_FOUND_WITH_SLUG, slug)));
    }

    @PostMapping
    public ObjectCreated addTag(@RequestBody TagCreateRequest request) {
        Tag tag = tagService.createTag(request);
        return new ObjectCreated(tag.getId(), ErrorMessage.TAG_CREATED);
    }

    @PutMapping("/{id}")
    public ObjectUpdated updateTag(@PathVariable Integer id,
                                   @RequestBody TagCreateRequest request) {
        tagService.updateTag(request, id);
        return new ObjectUpdated(ErrorMessage.TAG_UPDATED);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Integer id) {
        tagRepository.deleteById(id);
    }

}
