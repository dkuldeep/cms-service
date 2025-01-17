package com.cms.service;

import com.cms.constant.ErrorMessage;
import com.cms.dto.request.ToolCreateRequest;
import com.cms.entity.Tool;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.TagRepository;
import com.cms.repository.ToolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToolService {

    private static final Logger log = LoggerFactory.getLogger(ToolService.class);

    @Value("${header.tools}")
    private String headerTools;

    private final ToolRepository toolRepository;

    private final TagRepository tagRepository;

    public ToolService(ToolRepository toolRepository, TagRepository tagRepository) {
        this.toolRepository = toolRepository;
        this.tagRepository = tagRepository;
    }

    public List<Tool> findAll() {
        return toolRepository.findAll();
    }

    public Tool addNewTool(ToolCreateRequest request) {
        Tool tool = new Tool();
        tool.setCreated(LocalDateTime.now());
        mapRequestToTool(request, tool);
        return toolRepository.saveAndFlush(tool);
    }

    @Transactional
    public void updateTool(Integer id, ToolCreateRequest request) {
        Optional<Tool> optional = toolRepository.findById(id);
        if (optional.isPresent()) {
            Tool tool = optional.get();
            tool.setUpdated(LocalDateTime.now());
            mapRequestToTool(request, tool);
        } else {
            throw new ObjectNotFoundException(MessageFormat.format(ErrorMessage.TOOL_NOT_FOUND_WITH_ID, id));
        }
    }

    public Optional<Tool> getToolById(Integer id) {
        return toolRepository.findById(id);
    }

    public void deleteById(Integer id) {
        toolRepository.deleteById(id);
    }

    public List<Tool> findToolBySlug(String slug) {
        Tool search = new Tool();
        search.setSlug(slug);
        return toolRepository.findAll(Example.of(search));
    }

    public Optional<Tool> findBySlug(String slug) {
        Tool search = new Tool();
        search.setSlug(slug);
        return toolRepository.findOne(Example.of(search));
    }

    public List<Tool> getHeaderTools() {
        List<String> slugs = Arrays.stream(headerTools.trim().split(",")).toList();
        List<Tool> result = new ArrayList<>(0);
        for (String slug : slugs) {
            Tool search = new Tool();
            search.setSlug(slug);
            Optional<Tool> optionalTool = toolRepository.findOne(Example.of(search));
            optionalTool.ifPresent(result::add);
        }
        return result;
    }

    private void mapRequestToTool(ToolCreateRequest request, Tool tool) {
        tool.setName(request.getName());
        tool.setType(request.getType());
        tool.setContent(request.getContent());
        tool.setTagline(request.getTagline());
        tool.setTags(request.getTags().stream()
                .filter(Objects::nonNull)
                .map(tagRepository::getReferenceById)
                .collect(Collectors.toSet()));
        tool.setDescription(request.getDescription());
        tool.setSlug(request.getSlug());
        tool.setTitle(request.getTitle());
    }
}
