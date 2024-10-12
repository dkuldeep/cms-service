package com.cms.service;

import com.cms.constant.ErrorMessage;
import com.cms.dto.request.TagCreateRequest;
import com.cms.entity.Tag;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.TagRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Optional<Tag> findBySlug(String slug) {
        Tag tag = new Tag();
        tag.setSlug(slug);
        return tagRepository.findOne(Example.of(tag));
    }

    public Tag createTag(TagCreateRequest tagCreateRequest) {
        Tag tag = new Tag();
        mapRequestToTag(tagCreateRequest, tag);
        tag.setCreated(LocalDateTime.now());
        return tagRepository.saveAndFlush(tag);
    }

    @Transactional
    public void updateTag(TagCreateRequest tagCreateRequest, Integer id) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (optionalTag.isPresent()) {
            Tag existingTag = optionalTag.get();
            mapRequestToTag(tagCreateRequest, existingTag);
            existingTag.setUpdated(LocalDateTime.now());
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.TAG_NOT_FOUND_WITH_ID, id));
        }
    }

    private void mapRequestToTag(TagCreateRequest request, Tag tag) {
        tag.setName(request.getName());
        tag.setSlug(request.getSlug());
        tag.setTitle(request.getTitle());
        tag.setDescription(request.getDescription());
    }
}
