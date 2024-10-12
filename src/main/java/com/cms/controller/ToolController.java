package com.cms.controller;

import com.cms.constant.ErrorMessage;
import com.cms.constant.ToolType;
import com.cms.dto.DtoMapper;
import com.cms.dto.TypeDto;
import com.cms.dto.request.ToolCreateRequest;
import com.cms.dto.response.ObjectCreated;
import com.cms.dto.response.ObjectUpdated;
import com.cms.dto.response.ToolResponse;
import com.cms.dto.response.ToolResponseSnippet;
import com.cms.entity.Tool;
import com.cms.exception.ObjectNotFoundException;
import com.cms.service.HasSlug;
import com.cms.service.ToolService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tools")
public class ToolController implements HasSlug<ToolResponse> {

    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping
    public List<ToolResponseSnippet> getAllTools() {
        List<Tool> tools = toolService.findAll();
        return tools.stream().map(DtoMapper.TOOL_TO_SNIPPET).toList();
    }

    @GetMapping("/{id}")
    public ToolResponse getToolById(@PathVariable Integer id) {
        Optional<Tool> optional = toolService.getToolById(id);
        return optional.map(DtoMapper.TOOL_TO_DTO)
                .orElseThrow(() -> new ObjectNotFoundException(MessageFormat.format(ErrorMessage.TOOL_NOT_FOUND_WITH_ID, id)));
    }

    @PostMapping
    public ObjectCreated addNewTool(@RequestBody ToolCreateRequest request) {
        Tool tool = toolService.addNewTool(request);
        return new ObjectCreated(tool.getId(), ErrorMessage.TOOl_CREATED);
    }

    @PutMapping("/{id}")
    public ObjectUpdated updateTool(@PathVariable Integer id,
                                    @RequestBody ToolCreateRequest request) {
        toolService.updateTool(id, request);
        return new ObjectUpdated(ErrorMessage.TOOL_UPDATED);
    }

    @GetMapping("/types")
    public List<TypeDto> getToolTypes() {
        return Arrays.stream(ToolType.values())
                .map(toolType -> new TypeDto(toolType.name(), toolType.getLabel()))
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        toolService.deleteById(id);
    }

    @Override
    @GetMapping("/slug/{slug}")
    public ToolResponse getBySlug(@PathVariable String slug) {
        Optional<Tool> optional = toolService.findBySlug(slug);
        return optional.map(DtoMapper.TOOL_TO_DTO)
                .orElseThrow(() -> new ObjectNotFoundException(MessageFormat.format(ErrorMessage.TOOL_NOT_FOUND_WITH_SLUG, slug)));
    }

}
