package jsonbeautify.dto;

import jsonbeautify.entity.Post;

public class PostContentDto {
  private String content;

  public PostContentDto() {}

  public PostContentDto(Post post) {
    this.content = post.getContent();
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
