package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.constant.ToolType;
import com.cms.dto.DtoMapper;
import com.cms.dto.response.ObjectUpdated;
import com.cms.dto.response.ToolResponseDto;
import com.cms.dto.ToolTypeDto;
import com.cms.dto.request.ToolCreateRequest;
import com.cms.dto.response.ObjectCreated;
import com.cms.dto.wordpress.WordpressImportRequest;
import com.cms.entity.Tool;
import com.cms.exception.ObjectNotFoundException;
import com.cms.service.ImageService;
import com.cms.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tools")
public class ToolController {

    @Autowired
    private ToolService toolService;

    @Autowired
    private ImageService imageService;


    @GetMapping
    public List<ToolResponseDto> getAllTools(@RequestParam(required = false) String slug) {
        if (Objects.nonNull(slug)) {
            List<Tool> tools = toolService.findToolBySlug(slug);
            return tools.stream().map(DtoMapper.TOOL_TO_DTO).collect(Collectors.toList());
        }
        List<Tool> tools = toolService.findAll();
        return tools.stream().map(DtoMapper.TOOL_TO_DTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ToolResponseDto getToolById(@PathVariable Integer id) {
        Optional<Tool> optional = toolService.getToolById(id);
        return optional.map(DtoMapper.TOOL_TO_DTO)
                .orElseThrow(() -> new ObjectNotFoundException(MessageFormat.format(ErrorMessage.TOOL_NOT_FOUND_WITH_ID, id)));
    }

    @PostMapping
    public ObjectCreated addNewTool(@RequestBody ToolCreateRequest request) {
        Tool tool = toolService.addNewTool(request);
        return new ObjectCreated(tool.getId(), ErrorMessage.TOOl_CREATED);
    }

    @Transactional
    @PutMapping("/{id}")
    public ObjectUpdated updateTool(@PathVariable Integer id,
                                    @RequestBody ToolCreateRequest request) {
        toolService.updateTool(id, request);
        return new ObjectUpdated(ErrorMessage.TOOL_UPDATED);
    }

    @GetMapping("types")
    public List<ToolTypeDto> getToolTypes() {
        return Arrays.stream(ToolType.values())
                .map(toolType -> new ToolTypeDto(toolType.name(), toolType.getLabel()))
                .collect(Collectors.toList());
    }

    @PostMapping("wordpress")
    public ObjectCreated importFromWordpress(@RequestBody WordpressImportRequest request) throws MalformedURLException, URISyntaxException {
        toolService.importFromWordpress(request.getType(), request.getValue());
        return new ObjectCreated(-1, "Tools Created.");
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        toolService.deleteById(id);
    }
}
