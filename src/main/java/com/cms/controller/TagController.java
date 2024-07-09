package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.dto.DtoMapper;
import com.cms.dto.TagDto;
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
    public TagDto addTag(@RequestBody TagDto tagDto) {
        Tag tag = tagRepository.saveAndFlush(DtoMapper.DTO_TO_TAG.apply(tagDto));
        return DtoMapper.TAG_TO_DTO.apply(tag);
    }

    @Transactional
    @PutMapping("/{id}")
    public void updateTag(@PathVariable Integer id, @RequestBody TagDto tagDto) {
        Optional<Tag> optionalTag = tagRepository.findById(id);
        if (optionalTag.isPresent()) {
            Tag existingTag = optionalTag.get();
            existingTag.setName(tagDto.getName());
            existingTag.setDescription(tagDto.getDescription());
            existingTag.setSlug(tagDto.getSlug());
        } else {
            throw new ObjectNotFoundException(String.format(ErrorMessage.TAG_NOT_FOUND_WITH_ID, id));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Integer id) {
        tagRepository.deleteById(id);
    }
}
