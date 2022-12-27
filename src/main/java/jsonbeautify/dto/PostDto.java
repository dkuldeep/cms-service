package jsonbeautify.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto extends WebpageDto {
  private int id;
  private String type;
  private boolean active = true;
  private LocalDate created;
  private LocalDate modified;
  private List<TagDto> tags;
  private String path;

  private String reqTopic;
  private List<String> reqTags;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public LocalDate getModified() {
    return modified;
  }

  public void setModified(LocalDate modified) {
    this.modified = modified;
  }

  public LocalDate getCreated() {
    return created;
  }

  public void setCreated(LocalDate created) {
    this.created = created;
  }

  public List<TagDto> getTags() {
    return tags;
  }

  public void setTags(List<TagDto> tags) {
    this.tags = tags;
  }

  public String getReqTopic() {
    return reqTopic;
  }

  public void setReqTopic(String reqTopic) {
    this.reqTopic = reqTopic;
  }

  public List<String> getReqTags() {
    return reqTags;
  }

  public void setReqTags(List<String> reqTags) {
    this.reqTags = reqTags;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
