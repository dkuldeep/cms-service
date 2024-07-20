package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.dto.DtoMapper;
import com.cms.dto.response.TagDto;
import com.cms.dto.request.TagCreateRequest;
import com.cms.dto.response.ObjectCreated;
import com.cms.dto.response.ObjectUpdated;
import com.cms.entity.Tag;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public List<TagDto> getAll() {
        return tagRepository.findAll().stream().map(DtoMapper.TAG_TO_DTO).collect(Collectors.toList());
    }

    @PostMapping
    public ObjectCreated addTag(@RequestBody TagCreateRequest request) {
        Tag tag = new Tag();
        mapRequestToTag(request, tag);
        tag = tagRepository.saveAndFlush(tag);
        return new ObjectCreated(tag.getId(), ErrorMessage.TAG_CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    public ObjectUpdated updateTag(@PathVariable Integer id,
                                   @RequestBody TagCreateRequest request) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (optionalTag.isPresent()) {
            Tag existingTag = optionalTag.get();
            mapRequestToTag(request, existingTag);
            return new ObjectUpdated(ErrorMessage.TAG_UPDATED);
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.TAG_NOT_FOUND_WITH_ID, id));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Integer id) {
        tagRepository.deleteById(id);
    }

    private void mapRequestToTag(TagCreateRequest request, Tag tag) {
        tag.setName(request.getName());
        tag.setSlug(request.getSlug());
        tag.setTitle(request.getTitle());
        tag.setDescription(request.getDescription());
    }
}
