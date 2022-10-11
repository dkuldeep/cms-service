package jsonbeautify;

import jsonbeautify.dto.TopicDto;

import java.util.Arrays;

public enum TopicEnum {
  JAVASCRIPT(
      "javascript",
      "JavaScript",
      "/logo-javascript.png",
      "A high level programming language for the web. It runs on the browser and can make a static webpage interactive by dynamically updating and validating its content."),
  JAVA(
      "java",
      "Java",
      "/logo-java.jpg",
      "A high level, cross platform, object-oriented programming language originally developed by Sun Microsystems."),
  REACTJS(
      "reactjs",
      "ReactJS",
      "/logo-react.jpg",
      "A component based Javascript library for building awesome user interfaces."),
  MISC("misc", "Misc", "", "Misc");

  String name;
  String slug;
  String logoPath;
  String description;

  TopicEnum(String slug, String name, String logoPath, String description) {
    this.slug = slug;
    this.name = name;
    this.logoPath = logoPath;
    this.description = description;
  }

  public String getSlug() {
    return slug;
  }

  public static TopicEnum getBySlug(String slug) {
    return Arrays.stream(values()).filter(t -> t.slug.equals(slug)).findFirst().orElse(null);
  }

  public TopicDto toTopicDto() {
    TopicDto dto = new TopicDto();
    dto.setSlug(slug);
    dto.setName(name);
    dto.setLogoPath(logoPath);
    dto.setDescription(description);
    dto.setPath("/" + slug);
    return dto;
  }
}
