package com.cms.service;

import com.cms.business.ToolRef;
import com.cms.business.WordpressToolImpl;
import com.cms.constant.ErrorMessage;
import com.cms.dto.request.ToolCreateRequest;
import com.cms.dto.wordpress.WordpressPost;
import com.cms.entity.Tool;
import com.cms.exception.ObjectNotFoundException;
import com.cms.repository.TagRepository;
import com.cms.repository.ToolRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToolService {

    private static final Logger log = LoggerFactory.getLogger(ToolService.class);
    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private WordpressService wordpressService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private TagRepository tagRepository;

    public List<Tool> findAll() {
        return toolRepository.findAll();
    }

    public int importFromWordpress(String type, String value) throws MalformedURLException, URISyntaxException {
        List<WordpressPost> wordpressPosts = wordpressService.fetchPosts(value, type);
        List<Tool> tools = new ArrayList<>(0);
        for (WordpressPost wordpressPost : wordpressPosts) {
            Tool search = new Tool();
            search.setSlug(wordpressPost.getSlug());
            Example<Tool> toolExample = Example.of(search);
            if (toolRepository.exists(toolExample)) {
                log.warn("Tool with slug '{}' already exists, ignoring it.", wordpressPost.getSlug());
            } else {
                tools.add(addNewTool(new WordpressToolImpl(wordpressPost, imageService, tagRepository)));
            }
        }
        toolRepository.saveAllAndFlush(tools);
        return tools.size();
    }

    public Tool addNewTool(ToolRef toolRef) {
        Tool tool = new Tool();
        tool.setTagline(tool.getTagline());
        tool.setName(toolRef.getName());
        tool.setType(toolRef.getType());
        tool.setContent(toolRef.getContent());
        tool.setTitle(toolRef.getTitle());
        tool.setSlug(toolRef.getSlug());
        tool.setDescription(toolRef.getDescription());
        tool.setTags(new HashSet<>(toolRef.getTags()));
        return tool;
    }

    public Tool addNewTool(ToolCreateRequest request) {
        Tool tool = new Tool();
        tool.setCreated(LocalDateTime.now());
        mapRequestToTool(request, tool);
        return toolRepository.saveAndFlush(tool);
    }

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

    private void mapRequestToTool(ToolCreateRequest request, Tool tool) {
        tool.setName(request.getName());
        tool.setType(request.getType());
        tool.setContent(request.getContent());
        tool.setTagline(request.getTagline());
        tool.setTags(request.getTags().stream()
                .filter(Objects::nonNull)
                .map(integer -> tagRepository.getReferenceById(integer))
                .collect(Collectors.toSet()));
        tool.setDescription(request.getDescription());
        tool.setSlug(request.getSlug());
        tool.setTitle(request.getTitle());
    }
}
