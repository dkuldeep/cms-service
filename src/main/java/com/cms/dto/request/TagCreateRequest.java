package com.cms.dto.request;

import com.cms.dto.AbstractTagDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TagCreateRequest extends AbstractTagDto {
}
