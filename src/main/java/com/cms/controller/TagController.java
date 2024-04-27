package com.cms.controller;

import com.cms.dto.DtoMapper;
import com.cms.dto.TagDto;
import com.cms.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("")
    public List<TagDto> getAll() {
        return tagRepository.findAll().stream().map(DtoMapper.TAG_TO_DTO).collect(Collectors.toList());
    }

    @PostMapping("")
    public void addTag(@RequestBody TagDto tagDto) {
        tagRepository.saveAndFlush(DtoMapper.DTO_TO_TAG.apply(tagDto));
    }
}
