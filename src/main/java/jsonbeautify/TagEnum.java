package jsonbeautify;

import jsonbeautify.dto.TagDto;

import java.util.Arrays;

public enum TagEnum {
  JAVA("java", "Java"),
  JAVASCRIPT("javascript", "JavaScript"),
  XML("xml", "XML"),
  JSON("json", "JSON");

  String slug;
  String name;

  TagEnum(String slug, String name) {
    this.slug = slug;
    this.name = name;
  }

  public String getSlug() {
    return slug;
  }

  public String getName() {
    return name;
  }

  public static TagEnum getBySlug(String slug) {
    return Arrays.stream(values())
        .filter(tagEnum -> tagEnum.slug.equals(slug))
        .findFirst()
        .orElse(null);
  }

  public TagDto toTagDto() {
    TagDto dto = new TagDto();
    dto.setSlug(slug);
    dto.setName(name);
    dto.setPath("/tags/" + slug);
    return dto;
  }
}
